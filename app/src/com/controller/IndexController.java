package com.controller;

import com.services.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class IndexController extends BaseController {
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView navigateToIndex(HttpServletResponse response) {
        initControllerResources(context, request, response);
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();

        return new ModelAndView("index", model);
    }
}
