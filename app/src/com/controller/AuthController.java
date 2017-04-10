package com.controller;

import com.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.services.AuthService;
import com.services.ServiceManager;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AuthController extends BaseController {
    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    public ModelAndView visitAuthorizationForm(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        AuthService authService = ServiceManager.getInstance().getAuthService();
        if (authService.getLoggedUser() != null) {
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("auth", model);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth")
    public ModelAndView authorize(HttpServletResponse response) {
        initControllerResources(context, request, response);

        ServiceManager serviceManager = ServiceManager.getInstance();
        Map<String, Object> model = serviceManager.getSharedResources().getModel();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login != null && password != null) {
            AuthService authService = serviceManager.getAuthService();
            authService.login(login, password);
        }

        return new ModelAndView("auth_result", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ModelAndView logout(HttpServletResponse response) {
        initControllerResources(context, request, response);

        ServiceManager.getInstance().getAuthService().logout();

        return new ModelAndView("redirect:/");
    }
}
