package com.controller;

import com.services.ServiceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BaseController {
    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected HttpServletRequest request;

    protected void initControllerResources(ApplicationContext context, HttpServletRequest request, HttpServletResponse response) {
        ServiceManager.build(context, request, response);
    }
}
