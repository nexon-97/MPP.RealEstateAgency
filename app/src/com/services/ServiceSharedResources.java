package com.services;

import org.springframework.context.ApplicationContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ServiceSharedResources {
    Map<String, Object> getModel();
    HttpServletRequest getRequest();
    HttpServletResponse getResponse();
    ApplicationContext getApplicationContext();
    Cookie[] getCookies();
    void addCookie(Cookie cookie);
    void resetCookie(String cookieName);
}
