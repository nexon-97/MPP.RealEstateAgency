package com.controller;

import com.model.User;
import com.services.RegisterService;
import com.services.RegisterServiceImpl;
import com.services.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

@Controller
public class RegisterController extends BaseController{

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public ModelAndView visitRegistrationForm(HttpServletResponse response) {
        initControllerResources(context, request, response);
        return buildModelAndView("register");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ModelAndView register(HttpServletResponse response) {
        initControllerResources(context, request, response);
        RegisterService registerService = ServiceManager.getInstance().getRegisterService();
        boolean isRegisterCorrect = registerService.register(request.getParameterMap());
        if (isRegisterCorrect){
            return buildModelAndView("register_success");}
        else {
            return buildModelAndView("register");
        }
    }

}
