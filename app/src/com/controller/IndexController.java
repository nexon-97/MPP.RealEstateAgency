package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView navigateToIndex(HttpServletResponse response) {
        initControllerResources(context, request, response);

        return buildModelAndView("index");
    }
}
