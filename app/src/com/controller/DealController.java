package com.controller;

import com.dao.DealDAO;
import com.dao.impl.DealDAOImpl;
import com.helper.SystemMessages;
import com.model.*;
import com.services.DealRequestService;
import com.services.DealService;
import com.services.UserService;
import com.services.shared.ServiceManager;
import com.utils.request.ParseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class DealController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/deal")
    public ModelAndView showDeal(HttpServletResponse response) {
        initResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        if (!hasAdminRights()) {
            return showInsufficientRightsMessage();
        }

        int dealId;
        try {
            dealId = Integer.valueOf(request.getParameter("id"));
        } catch (NullPointerException | NumberFormatException e) {
            model.put("msg", "Такой стелки не существует!");
            return buildModelAndView("../error_message");
        }

        DealDAO dao = new DealDAOImpl();
        Deal deal = dao.get(dealId);
        if (deal != null) {
            model.put("deal", deal);
        } else {
            model.put("msg", "Такой стелки не существует!");
            return buildModelAndView("../error_message");
        }

        return buildModelAndView("deal_view");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addDealRequest")
    public ModelAndView addOfferRequest(HttpServletResponse response) {
        initResources(response);

        ServiceManager serviceManager = ServiceManager.getInstance();
        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            if (!loggedUser.getRoleId().equals(RoleId.User)) {
                return showErrorMessage(HttpServletResponse.SC_FORBIDDEN, "Вы не имеете право откликаться на предложения!");
            }

            Integer offerId = getIdFromRequest();
            Integer buyerId = getBuyerIdFromRequest();

            if (offerId == null || buyerId == null) {
                return showErrorMessage(HttpServletResponse.SC_BAD_REQUEST, "Отклик на предложение поврежден!");
            }

            Offer offer;
            User buyer;

            offer = serviceManager.getOfferService().get(offerId);
            buyer = serviceManager.getUserService().get(buyerId);

            DealRequest dealRequest = new DealRequest();
            dealRequest.setOffer(offer);
            dealRequest.setBuyer(buyer);

            if (serviceManager.getDealRequestService().isAlreadyRegistered(dealRequest)) {
                return showErrorMessage(HttpServletResponse.SC_BAD_REQUEST, SystemMessages.SuchDealRequestAlreadyRegistered);
            }

            // Add optional realtor
            Integer realtorId = getRealtorIdFromRequest();
            if (realtorId == null) {
                // Add model param to construct realtors selector
                Map<String, Object> model = serviceManager.getSharedResources().getModel();

                UserService userService = serviceManager.getUserService();
                List<User> realtorsList = userService.getUsersByRole(RoleId.Rieltor);

                model.put("realtorsList", realtorsList);
                model.put("offer", offer);

                return buildModelAndView("add_deal_request_view");
            } else if (realtorId != 0) {
                boolean realtorAdded = addDealRequestRealtor(dealRequest);
                if (!realtorAdded) {
                    return showErrorMessage(HttpServletResponse.SC_BAD_REQUEST, SystemMessages.FailedToAddRealtorToDealRequest);
                }
            }

            serviceManager.getDealRequestService().add(dealRequest);

            return showSuccessMessage(SystemMessages.DealRequestHasBeenRegistered);
        }

        return showUnauthorizedMessageView();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/confirmDealRealtor")
    public ModelAndView confirmDealRequestRealtor(HttpServletResponse response) {
        initResources(response);

        ServiceManager serviceManager = ServiceManager.getInstance();
        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null && loggedUser.getRoleId().equals(RoleId.Rieltor)) {
            Integer requestId = getIdFromRequest();
            Integer realtorId = getRealtorIdFromRequest();

            if (requestId != null && realtorId != null) {
                    DealRequestService dealRequestService = serviceManager.getDealRequestService();
                    DealRequest request = dealRequestService.get(requestId);

                    if (!request.getRealtor().equals(loggedUser)) {
                        return showErrorMessage(HttpServletResponse.SC_BAD_REQUEST, "Вы не были помечены как риэлтор в этом отклике!");
                    }

                    request.setRealtorValidation(true);
                    dealRequestService.update(request);

                    return redirect("/profile");
            } else {
                return showErrorMessage(HttpServletResponse.SC_BAD_REQUEST, "Не удалось изменить риэлтора в отклике на предложение!");
            }
        }

        return showErrorMessage("Вы не риэлтор!");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/confirmDealSeller")
    public ModelAndView confirmDealRequestSeller(HttpServletResponse response) {
        initResources(response);

        ServiceManager serviceManager = ServiceManager.getInstance();
        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            Integer requestId = getIdFromRequest();
            Integer sellerId = getSellerIdFromRequest();

            if (requestId != null && sellerId != null) {
                    DealRequestService dealRequestService = serviceManager.getDealRequestService();
                    DealRequest request = dealRequestService.get(requestId);

                    if (!request.getOffer().getProperty().getOwner().equals(loggedUser)) {
                        return showErrorMessage("Вы не владелец данной собственности!");
                    }

                    request.setSellerValidation(true);
                    dealRequestService.update(request);

                    return redirect("/profile");
            } else {
                return showErrorMessage("Не удалось подтвердить отклик на предложение!");
            }
        }

        return showUnauthorizedMessageView();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/confirmDealBroker")
    public ModelAndView confirmDealBroker(HttpServletResponse response) {
        initResources(response);

        User currentUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        if (!currentUser.getRoleId().equals(RoleId.Broker)) {
            return showErrorMessage(SystemMessages.InsufficientRightsMessage);
        }

        Integer id = getIdFromRequest();
        if (id != null) {
            DealService dealService = ServiceManager.getInstance().getDealService();
            Deal deal = dealService.get(id);

            if (dealService.signDeal(deal, currentUser)) {
                return redirectToReferer();
            } else {
                return showErrorMessage(HttpServletResponse.SC_BAD_REQUEST, "Не удалось закрепить брокера за сделкой!");
            }
        } else {
            return showErrorMessage(HttpServletResponse.SC_BAD_REQUEST, SystemMessages.NoDealIdProvidedMessage);
        }
    }

    private boolean hasAdminRights() {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        return loggedUser != null && loggedUser.getRoleId().equals(RoleId.Admin);
    }

    private Integer getBuyerIdFromRequest() {
        String buyerIdParam = request.getParameter("buyer");
        if (buyerIdParam != null) {
            return ParseUtils.parseInteger(buyerIdParam);
        }

        return null;
    }

    private Integer getRealtorIdFromRequest() {
        String realtorIdParam = request.getParameter("realtor");
        if (realtorIdParam != null) {
            return ParseUtils.parseInteger(realtorIdParam);
        }

        return null;
    }

    private Integer getSellerIdFromRequest() {
        String sellerIdParam = request.getParameter("seller");
        if (sellerIdParam != null) {
            return ParseUtils.parseInteger(sellerIdParam);
        }

        return null;
    }

    private boolean addDealRequestRealtor(DealRequest request) {
        Integer realtorId = getRealtorIdFromRequest();
        if (realtorId != null) {
            User realtor = ServiceManager.getInstance().getUserService().get(realtorId);

            request.setRealtor(realtor);
            request.setRealtorValidation(false);

            return true;
        }

        return false;
    }

    private ModelAndView showInsufficientRightsMessage() {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", "Нужны права администратора для просмотра этой страницы!");
        return buildModelAndView("../error_message");
    }
}
