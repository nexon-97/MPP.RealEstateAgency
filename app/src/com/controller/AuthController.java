package com.controller;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface AuthController {
    ModelAndView visitAuthorizationForm();
    ModelAndView authorize(HttpServletResponse response);
    ModelAndView logout(HttpServletResponse response);
}
