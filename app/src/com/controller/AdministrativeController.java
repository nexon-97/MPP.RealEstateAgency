package com.controller;

import com.helper.SystemMessages;
import com.model.*;
import com.services.DealService;
import com.services.OfferService;
import com.services.TransactionService;
import com.services.TransactionServiceImpl;
import com.services.shared.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdministrativeController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/broker")
    public ModelAndView visitBrokerView(HttpServletResponse response) {
        initControllerResources(response);

        if (!checkRights(RoleId.Broker)) {
            return showErrorMessage(SystemMessages.InsufficientRightsMessage);
        }

        DealService dealService = ServiceManager.getInstance().getDealService();
        User currentUser = ServiceManager.getInstance().getAuthService().getLoggedUser();

        List<Deal> dealsHistory = dealService.listValidatedBrokerDeals(currentUser);
        List<Deal> upcomingDeals = dealService.listUpcomingBrokerDeals(currentUser);

        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("dealsHistory", dealsHistory);
        model.put("upcomingDeals", upcomingDeals);

        return buildModelAndView("broker_view");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/confirmDealBroker")
    public ModelAndView confirmDealBroker(HttpServletResponse response) {
        initControllerResources(response);

        if (!checkRights(RoleId.Broker)) {
            return showErrorMessage(SystemMessages.InsufficientRightsMessage);
        }

        DealService dealService = ServiceManager.getInstance().getDealService();
        Integer id = getIdFromRequest();
        if (id != null) {
            Deal deal = dealService.getDealById(id);

            if (deal!= null) {
                User currentUser = ServiceManager.getInstance().getAuthService().getLoggedUser();

                if (dealService.assignDealBroker(deal, currentUser)) {
                    return redirect("/broker");
                }
            } else {
                return showErrorMessage(SystemMessages.NoSuchDealMessage);
            }
        } else {
            return showErrorMessage(SystemMessages.NoDealIdProvidedMessage);
        }

        return showErrorMessage(SystemMessages.FailedToConfirmBrokerMessage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/realtor")
    public ModelAndView visitRealtorView(HttpServletResponse response) {
        initControllerResources(response);

        if (!checkRights(RoleId.Rieltor)) {
            return showErrorMessage(SystemMessages.InsufficientRightsMessage);
        }

        return buildModelAndView("realtor_view");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin_data")
    public ModelAndView visitAdminData(HttpServletResponse response) {
        initControllerResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        if (!checkRights(RoleId.Admin)) {
            return showErrorMessage(SystemMessages.InsufficientRightsMessage);
        }

        List<User> users = ServiceManager.getInstance().getUserService().getList();
        List<Property> properties = ServiceManager.getInstance().getPropertyService().getList();
        List<Offer> offers = ServiceManager.getInstance().getOfferService().listAllOffers();
        List<Deal> deals = ServiceManager.getInstance().getDealService().list();
        Map<Property, Boolean> hasOfferMap = getHasOfferMap(properties);
        Map<Offer, Boolean> hasDealMap = getHasDealMap(offers);

        model.put("userList", users);
        model.put("propertyList", properties);
        model.put("offerList", offers);
        model.put("dealList", deals);
        model.put("hasOffer", hasOfferMap);
        model.put("hasDeal", hasDealMap);

        return buildModelAndView("admin_data");
    }

    boolean checkRights(RoleId roleId) {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        return (loggedUser != null && loggedUser.getRoleId().equals(roleId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/companyWork")
    public ModelAndView getCompanyWorkReport(HttpServletResponse response) {
        initControllerResources(response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();

        if (loggedUser != null && loggedUser.getRoleId().equals(RoleId.Admin)){
            TransactionService transactionService = ServiceManager.getInstance().getTransactionService();
            List<Transaction> transactions = transactionService.getTransactionsList();
            model.put("transactions", transactions);
            BigDecimal sumCompanyFine = BigDecimal.ZERO;
            for (Transaction transaction : transactions){
                sumCompanyFine = sumCompanyFine.add(transaction.getCompanyFine());
            }
            model.put("totalFine", sumCompanyFine);
            return buildModelAndView("../company_work");
        }

        String msgError = "У вас нет прав для просмотра этой страницы!";
        model.put("msg", msgError);

        return buildModelAndView("../error_message");

    }

    private Map<Property, Boolean> getHasOfferMap(List<Property> properties){
        OfferService offerService = ServiceManager.getInstance().getOfferService();
        Map<Property, Boolean> hasOfferMap = new HashMap<>();
        for(Property property: properties){
            boolean bool = offerService.hasOfferOnProperty(property);
            hasOfferMap.put(property, bool);
        }
        return hasOfferMap;
    }

    private Map<Offer, Boolean> getHasDealMap(List<Offer> offers){
        DealService dealService = ServiceManager.getInstance().getDealService();
        Map<Offer, Boolean> hasDealMap = new HashMap<>();
        for(Offer offer: offers){
            boolean bool = dealService.hasDealOnOffer(offer);
            hasDealMap.put(offer, bool);
        }
        return hasDealMap;
    }
}
