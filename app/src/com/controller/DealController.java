package com.controller;

import com.dao.DealDAO;
import com.dao.DealDAOImpl;
import com.helper.SystemMessages;
import com.model.*;
import com.services.AuthService;
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

    @RequestMapping(method = RequestMethod.GET, value = "/deals")
    public ModelAndView showAllDeals(HttpServletResponse response) {
        initControllerResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        if (!hasAdminRights()) {
            model.put("msg", "Нужны права администратора для просмотра этой страницы!");
            return buildModelAndView("../error_message");
        }

        DealDAO dao = new DealDAOImpl();
        List<Deal> deals = dao.list();
        model.put("deals", deals);

        return buildModelAndView("deals_view");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deal")
    public ModelAndView showDeal(HttpServletResponse response) {
        initControllerResources(response);
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
        Deal deal = dao.getDealById(dealId);
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
        initControllerResources(response);

        ServiceManager serviceManager = ServiceManager.getInstance();
        if (serviceManager.getAuthService().isUserLoggedIn()) {
            Integer offerId = getIdFromRequest();
            Integer buyerId = getBuyerIdFromRequest();

            if (offerId == null || buyerId == null) {
                return showErrorMessage("Отклик на предложение поврежден!");
            }

            Offer offer = serviceManager.getOfferService().getOfferById(offerId);
            if (offer == null) {
                return showErrorMessage(SystemMessages.NoSuchOfferMessage);
            }

            User buyer = serviceManager.getUserService().getUserByID(buyerId);
            if (buyer == null) {
                return showErrorMessage(SystemMessages.InvalidDealRequestBuyerAssigned);
            }

            DealRequest dealRequest = new DealRequest();
            dealRequest.setOffer(offer);
            dealRequest.setBuyer(buyer);

            if (serviceManager.getDealService().isDealRequestRegistered(dealRequest)) {
                return showErrorMessage(SystemMessages.SuchDealRequestAlreadyRegistered);
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
                    return showErrorMessage(SystemMessages.FailedToAddRealtorToDealRequest);
                }
            }

            boolean requestAdded = serviceManager.getDealService().addDealRequest(dealRequest);
            if (!requestAdded) {
                return showErrorMessage(SystemMessages.FailedToAddDealRequest);
            } else {
                return showSuccessMessage(SystemMessages.DealRequestHasBeenRegistered);
            }
        }

        return showUnauthorizedMessageView();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/confirmDealRealtor")
    public ModelAndView confirmDealRequestRealtor(HttpServletResponse response) {
        initControllerResources(response);

        ServiceManager serviceManager = ServiceManager.getInstance();
        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null && loggedUser.getRoleId().equals(RoleId.Rieltor)) {
            Integer requestId = getIdFromRequest();
            Integer realtorId = getRealtorIdFromRequest();

            if (requestId != null && realtorId != null) {
                DealRequest request = serviceManager.getDealService().getDealRequestById(requestId);

                if (request != null) {
                    if (!request.getRealtor().equals(loggedUser)) {
                        return showErrorMessage("Вы не были помечены как риэлтор в этом отклике!");
                    }

                    request.setRealtorValidation(true);
                    serviceManager.getDealService().updateDealRequest(request);

                    return redirect("/profile");
                } else {
                    return showErrorMessage("Такого отклика не существует!");
                }
            } else {
                return showErrorMessage("Не удалось изменить риэлтора в отклике на предложение!");
            }
        }

        return showErrorMessage("Вы не риэлтор!");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/confirmDealSeller")
    public ModelAndView confirmDealRequestSeller(HttpServletResponse response) {
        initControllerResources(response);

        ServiceManager serviceManager = ServiceManager.getInstance();
        User loggedUser = serviceManager.getAuthService().getLoggedUser();
        if (loggedUser != null) {
            Integer requestId = getIdFromRequest();
            Integer sellerId = getSellerIdFromRequest();

            if (requestId != null && sellerId != null) {
                DealRequest request = serviceManager.getDealService().getDealRequestById(requestId);

                if (request != null) {
                    if (!request.getOffer().getProperty().getOwner().equals(loggedUser)) {
                        return showErrorMessage("Вы не владелец данной собственности!");
                    }

                    request.setSellerValidation(true);
                    serviceManager.getDealService().updateDealRequest(request);

                    return redirect("/profile");
                } else {
                    return showErrorMessage("Такого отклика не существует!");
                }
            } else {
                return showErrorMessage("Не удалось подтвердить отклик на предложение!");
            }
        }

        return showUnauthorizedMessageView();
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
            User realtor = ServiceManager.getInstance().getUserService().getUserByID(realtorId);

            if (realtor != null) {
                request.setRealtor(realtor);
                request.setRealtorValidation(false);

                return true;
            }
        }

        return false;
    }

    private ModelAndView showInsufficientRightsMessage() {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", "Нужны права администратора для просмотра этой страницы!");
        return buildModelAndView("../error_message");
    }
}
