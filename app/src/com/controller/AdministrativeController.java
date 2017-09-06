package com.controller;

import com.model.*;
import com.security.annotations.RoleCheck;
import com.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdministrativeController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    OfferService offerService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    DealService dealService;

    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/admin_data")
    @RoleCheck(RoleId.Admin)
    public String visitAdminData(Model model) {
        List<User> users = userService.getList();
        List<Property> properties = propertyService.list();
        List<Offer> offers = offerService.listAllOffers();
        List<Deal> deals = dealService.list();
        Map<Property, Boolean> hasOfferMap = getHasOfferMap(properties);
        Map<Offer, Boolean> hasDealMap = getHasDealMap(offers);

        model.addAttribute("userList", users);
        model.addAttribute("propertyList", properties);
        model.addAttribute("offerList", offers);
        model.addAttribute("dealList", deals);
        model.addAttribute("hasOffer", hasOfferMap);
        model.addAttribute("hasDeal", hasDealMap);

        return "admin_views/admin_data";
    }

    @GetMapping(value = "/companyWork")
    @RoleCheck(RoleId.Admin)
    public String getCompanyWorkReport(Model model) {
        List<Transaction> transactions = transactionService.getTransactionsList();
        model.addAttribute("transactions", transactions);

        BigDecimal sumCompanyFine = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            sumCompanyFine = sumCompanyFine.add(transaction.getCompanyFine());
        }

        model.addAttribute("totalFine", sumCompanyFine);
        return "company_work";
    }

    private Map<Property, Boolean> getHasOfferMap(List<Property> properties){
        Map<Property, Boolean> hasOfferMap = new HashMap<>();
        for(Property property: properties) {
            boolean bool = offerService.hasOfferOnProperty(property);
            hasOfferMap.put(property, bool);
        }

        return hasOfferMap;
    }

    private Map<Offer, Boolean> getHasDealMap(List<Offer> offers){
        Map<Offer, Boolean> hasDealMap = new HashMap<>();
        for(Offer offer: offers) {
            boolean bool = dealService.hasDealOnOffer(offer);
            hasDealMap.put(offer, bool);
        }

        return hasDealMap;
    }
}
