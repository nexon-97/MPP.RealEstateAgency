package com.services;

import org.springframework.context.ApplicationContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ServiceSharedResourcesImpl implements ServiceSharedResources {
    private Map<String, Object> model;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ApplicationContext appContext;

    public ServiceSharedResourcesImpl(ApplicationContext appContext, HttpServletRequest request, HttpServletResponse response) {
        this.appContext = appContext;
        this.request = request;
        this.response = response;

        this.model = new HashMap<>();
    }

    @Override
    public Map<String, Object> getModel() {
        return model;
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public HttpServletResponse getResponse() {
        return response;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return appContext;
    }

    @Override
    public Cookie[] getCookies() {
        return request.getCookies();
    }

    @Override
    public void addCookie(Cookie cookie) {
        response.addCookie(cookie);
    }

    @Override
    public void resetCookie(String cookieName) {
        Cookie removedCookie = new Cookie(cookieName, new String());
        removedCookie.setMaxAge(0);
        response.addCookie(removedCookie);
    }
}
