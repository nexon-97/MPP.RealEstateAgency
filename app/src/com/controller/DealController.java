package com.controller;

import com.dao.DealDAO;
import com.dao.DealDAOImpl;
import com.model.Deal;
import com.model.RoleId;
import com.model.User;
import com.services.AuthService;
import com.services.shared.ServiceManager;
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
        initControllerResources(context, request, response);
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
        initControllerResources(context, request, response);
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

    private boolean hasAdminRights() {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        return loggedUser != null && loggedUser.getRole().getRoleId().equals(RoleId.Admin);
    }

    private ModelAndView showInsufficientRightsMessage() {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", "Нужны права администратора для просмотра этой страницы!");
        return buildModelAndView("../error_message");
    }
}
