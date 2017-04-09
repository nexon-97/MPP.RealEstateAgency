package com.controller;

import com.dao.UserDAO;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.services.AuthService;
import com.services.ServiceManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthControllerImpl implements AuthController {
    @Autowired
    ApplicationContext context;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    public ModelAndView visitAuthorizationForm() {
        Map<String, Object> model = new HashMap<>();
        UserDAO userDAO = context.getBean(UserDAO.class);

        /*AuthService authService = (AuthService)ServiceManager.getInstance().getService("AuthService");
        Cookie[] cookies = request.getCookies();
        if (authService.loginFromCookies(cookies, userDAO)) {
            User loggedUser = authService.getLoggedUser();

            model.put("loginSucceeded", true);
            model.put("login", loggedUser.getLogin());
            return new ModelAndView("auth_result", model);
        }*/

        return new ModelAndView("auth", model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth")
    public ModelAndView authorize(HttpServletResponse response) {
        Map<String, Object> model = new HashMap<>();
        UserDAO userDAO = context.getBean(UserDAO.class);

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login != null && password != null) {
            AuthService authService = (AuthService)ServiceManager.getInstance().getService("AuthService");

            boolean loginSucceeded = authService.login(login, password, userDAO);
            model.put("loginSucceeded", loginSucceeded);

            if (loginSucceeded) {
                User loggedUser = authService.getLoggedUser();

                model.put("login", loggedUser.getLogin());

                // Put login data to cookies
                Cookie loginCookie = buildLoginCookie("auth_login", loggedUser.getLogin());
                Cookie passCookie = buildLoginCookie("auth_pass", loggedUser.getPasswordHash());

                response.addCookie(loginCookie);
                response.addCookie(passCookie);
            }
        }

        return new ModelAndView("auth_result", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    @Override
    public ModelAndView logout(HttpServletResponse response) {
        resetCookie(response, "auth_login");
        resetCookie(response, "auth_pass");

        return new ModelAndView("auth");
    }

    private void resetCookie(HttpServletResponse response, String cookieName) {
        Cookie removedCookie = new Cookie(cookieName, new String());
        removedCookie.setMaxAge(0);
        response.addCookie(removedCookie);
    }

    private Cookie buildLoginCookie(String name, String value) {
        int cookieDuration = 60 * 60 * 24 * 7;
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(cookieDuration);

        return cookie;
    }
}
