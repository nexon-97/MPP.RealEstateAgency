package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.services.AuthService;
import com.services.shared.ServiceManager;

@Controller
public class AuthController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    public ModelAndView visitAuthorizationForm(HttpServletResponse response) {
        initControllerResources(response);

        AuthService authService = ServiceManager.getInstance().getAuthService();
        if (authService.getLoggedUser() != null) {
            return redirect("/");
        } else {
            return buildModelAndView("auth");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth")
    public ModelAndView authorize(HttpServletResponse response) {
        initControllerResources(response);

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login != null && password != null) {
            AuthService authService = ServiceManager.getInstance().getAuthService();
            authService.login(login, password);
        }

        return buildModelAndView("auth_result");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ModelAndView logout(HttpServletResponse response) {
        initControllerResources(response);

        ServiceManager.getInstance().getAuthService().logout();

        return redirect("/");
    }
}
