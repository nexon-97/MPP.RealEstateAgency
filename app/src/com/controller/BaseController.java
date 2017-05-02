package com.controller;

import com.services.shared.ServiceManager;

import com.utils.request.ParseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

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

    protected ModelAndView showUnauthorizedMessageView() {
        JstlView view = new JstlView("views/unauthorized_view.jsp");
        return new ModelAndView(view, ServiceManager.getInstance().getSharedResources().getModel());
    }

    protected ModelAndView showErrorMessage(String message) {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", message);

        JstlView view = new JstlView("views/error_message.jsp");
        return new ModelAndView(view, model);
    }

    protected Integer getIdFromRequest() {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            return ParseUtils.parseInteger(idParam);
        }

        return null;
    }
}
