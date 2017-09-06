package com.controller;

import com.exception.*;
import com.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
    protected ModelAndView handleUnauthorizedException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("unauthorized_view");
        return modelAndView;
    }

    @ExceptionHandler(AccessLevelException.class)
    protected ModelAndView handleAccessLevelException() {
        return showErrorMessage("У Вас недостаточно прав для выполнения данного действия!");
    }

    @ExceptionHandler(GenericException.class)
    protected ModelAndView handleGenericException(GenericException ex) {
        return showErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    protected ModelAndView handleMissingParams() {
        return showBadRequestView();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ModelAndView handleMethodArgumentTypeMismatch() {
        return showBadRequestView();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ModelAndView handleEntityNotFound(EntityNotFoundException ex) {
        return showErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(RoleMismatchException.class)
    protected ModelAndView handleRoleMismatch(RoleMismatchException ex) {
        return showErrorMessage(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ModelAndView showBadRequestView() {
        return showErrorMessage("Запрос поврежден!");
    }

    protected ModelAndView showErrorMessage(String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("msg", message);
        modelAndView.setViewName("error_message");
        attachAuthDataToExceptionReport(modelAndView);
        return modelAndView;
    }

    protected void attachAuthDataToExceptionReport(ModelAndView modelAndView) {
        modelAndView.getModel().put("user", authService.getLoggedUser());
    }
}
