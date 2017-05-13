package com.controller;

import com.model.User;
import com.services.shared.ServiceManager;

import com.utils.request.ErrorRegistry;
import com.utils.request.ParseUtils;
import com.utils.request.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
public class BaseController implements ErrorRegistry {

    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected HttpServletRequest request;

    protected HttpServletResponse response;
    protected ResponseData responseData;

    protected void initResources(HttpServletResponse response) {
        ServiceManager.build(context, request, response);
        this.response = response;
        this.responseData = new ResponseData(HttpServletResponse.SC_OK, null);

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding!");
        }
    }

    protected ModelAndView finalizeController(ResponseData responseData) {
        this.response.setStatus(responseData.getStatus());
        return this.responseData.getModelAndView();
    }

    protected ModelAndView finalizeController(String viewName) {
        this.response.setStatus(HttpServletResponse.SC_OK);
        return new ModelAndView(viewName, getModel());
    }

    protected ModelAndView finalizeController(int status, String viewName) {
        this.response.setStatus(status);
        return new ModelAndView(viewName, getModel());
    }

    protected ModelAndView finalizeController(int status, View view) {
        this.response.setStatus(status);
        return new ModelAndView(view, getModel());
    }

    protected ModelAndView buildModelAndView(String viewName) {
        return new ModelAndView(viewName, ServiceManager.getInstance().getSharedResources().getModel());
    }

    protected ModelAndView buildModelAndView(int responseCode, String viewName) {
        this.response.setStatus(responseCode);
        return new ModelAndView(viewName, ServiceManager.getInstance().getSharedResources().getModel());
    }

    protected ModelAndView redirect(String path) {
        return new ModelAndView("redirect:" + path);
    }

    protected ModelAndView redirectToReferer() {
        String referer = request.getHeader("Referer");
        if (referer != null) {
            return redirect(referer);
        } else {
            return redirect("/");
        }
    }

    protected ModelAndView showUnauthorizedMessageView() {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        JstlView view = new JstlView("views/unauthorized_view.jsp");
        return new ModelAndView(view, ServiceManager.getInstance().getSharedResources().getModel());
    }

    protected ModelAndView showBadRequestView(String message) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return showErrorMessage(message);
    }

    protected ModelAndView showErrorMessage(int responseCode, String message) {
        response.setStatus(responseCode);
        return showErrorMessage(message);
    }

    protected ModelAndView showErrorMessage(String message) {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", message);

        JstlView view = new JstlView("views/error_message.jsp");
        return new ModelAndView(view, model);
    }

    protected View getErrorMessageView() {
        return new JstlView("views/error_message.jsp");
    }

    protected ModelAndView showSuccessMessage(String message) {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", message);

        JstlView view = new JstlView("views/success_message.jsp");
        return new ModelAndView(view, model);
    }

    protected ModelAndView showAccessForbiddenMessage(String message) {
        return showErrorMessage(HttpServletResponse.SC_FORBIDDEN, message);
    }

    protected Integer getIdFromRequest() {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            return ParseUtils.parseInteger(idParam);
        }

        return null;
    }

    protected Map<String, Object> getModel() {
        return ServiceManager.getInstance().getSharedResources().getModel();
    }

    protected void putModelItem(String name, Object object) {
        ServiceManager.getInstance().getSharedResources().getModel().put(name, object);
    }

    protected User getCurrentUser() {
        return ServiceManager.getInstance().getAuthService().getLoggedUser();
    }

    @Override
    public ResponseData getResponseData() {
        return this.responseData;
    }

    protected void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }
}
