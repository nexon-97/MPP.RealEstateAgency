package com.controller;

import com.model.*;
import com.services.AuthService;
import com.services.DealService;
import com.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdministrativeController extends BaseController {

    @Autowired
    AuthService authService;

    @Autowired
    OfferService offerService;

    @Autowired
    DealService dealService;

    @GetMapping(value = "/admin_data")
    public ModelAndView visitAdminData(HttpServletResponse response) {
        /*initControllerResources(response);
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
        model.put("hasDeal", hasDealMap);*/

       // return buildModelAndView("admin_data");
        return null;
    }

    boolean checkRights(RoleId roleId) {
        User loggedUser = authService.getLoggedUser();
        return (loggedUser != null && loggedUser.getRoleId().equals(roleId));
    }

    @GetMapping(value = "/companyWork")
    public ModelAndView getCompanyWorkReport(HttpServletResponse response) {
        /*initControllerResources(response);
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
        model.put("msg", msgError);*/

        //return buildModelAndView("../error_message");
        return null;
    }

    private Map<Property, Boolean> getHasOfferMap(List<Property> properties){
        Map<Property, Boolean> hasOfferMap = new HashMap<>();
        for(Property property: properties){
            boolean bool = offerService.hasOfferOnProperty(property);
            hasOfferMap.put(property, bool);
        }
        return hasOfferMap;
    }

    private Map<Offer, Boolean> getHasDealMap(List<Offer> offers){
        Map<Offer, Boolean> hasDealMap = new HashMap<>();
        for(Offer offer: offers){
            boolean bool = dealService.hasDealOnOffer(offer);
            hasDealMap.put(offer, bool);
        }
        return hasDealMap;
    }
}
