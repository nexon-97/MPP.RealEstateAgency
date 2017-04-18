package com.controller;

import com.services.shared.ServiceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class BaseController {

    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected HttpServletRequest request;

    protected void initControllerResources(ApplicationContext context, HttpServletRequest request, HttpServletResponse response) {
        ServiceManager.build(context, request, response);

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding!");
        }
    }

    protected ModelAndView buildModelAndView(String viewName) {
        return new ModelAndView(viewName, ServiceManager.getInstance().getSharedResources().getModel());
    }

    protected ModelAndView redirect(String path) {
        return new ModelAndView("redirect:" + path);
    }
}
