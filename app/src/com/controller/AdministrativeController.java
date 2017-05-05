package com.controller;

import com.helper.SystemMessages;
import com.model.Deal;
import com.model.RoleId;
import com.model.User;
import com.services.DealService;
import com.services.shared.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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

    boolean checkRights(RoleId roleId) {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        return (loggedUser != null && loggedUser.getRoleId().equals(roleId));
    }
}
