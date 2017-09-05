package com.services;

import com.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    boolean loginFromCookies(HttpServletRequest request);
    boolean login(String login, String password, HttpServletResponse response);
    User getLoggedUser();
    boolean isUserLoggedIn();
    boolean logout(HttpServletResponse response);
}
