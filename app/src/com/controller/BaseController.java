package com.controller;

import com.exception.AccessLevelException;
import com.exception.UnauthorizedException;
import com.security.AccessLevel;
import com.services.AuthService;
import com.utils.request.ParseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected ViewResolver viewResolver;

    @Autowired
    protected AuthService authService;


    protected String redirect(String path) {
        return "redirect:" + path;
    }

    protected String redirectToReferer() {
        String referer = request.getHeader("Referer");
        if (referer != null) {
            return redirect(referer);
        } else {
            return redirect("/");
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView handleUnauthorizedException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("unauthorized_view");

        return modelAndView;
    }

    @ExceptionHandler(AccessLevelException.class)
    public ModelAndView handleAccessLevelException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("msg", "У Вас недостаточно прав для выполнения данного действия!");
        modelAndView.setViewName("error_message");
        return modelAndView;
    }

    protected ModelAndView showBadRequestView(String message) {
        //response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return showErrorMessage(message);
    }

    protected ModelAndView showErrorMessage(int responseCode, String message) {
        //response.setStatus(responseCode);
        return showErrorMessage(message);
    }

    protected ModelAndView showErrorMessage(String message) {
        //Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        //model.put("msg", message);

        JstlView view = new JstlView("views/error_message.jsp");
        return new ModelAndView(view, null);
    }

    protected ModelAndView showSuccessMessage(String message) {
        //Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        //model.put("msg", message);

        JstlView view = new JstlView("views/success_message.jsp");
        return new ModelAndView(view, null);
    }

    protected Integer getIdFromRequest() {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            return ParseUtils.parseInteger(idParam);
        }

        return null;
    }
}
