package com.controller;

import com.model.User;
import com.services.RegisterService;
import com.services.shared.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class RegisterController extends BaseController{

    private User user;
   // boolean register(String login, String password);

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public ModelAndView visitRegistrationForm(HttpServletResponse response) {
        initControllerResources(context, request, response);
        RegisterService registerService = ServiceManager.getInstance().getRegisterService();



        return buildModelAndView("register");
    }

}
