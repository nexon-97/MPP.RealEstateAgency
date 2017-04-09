package com.services;

import com.dao.UserDAO;
import com.model.User;

import javax.servlet.http.Cookie;

public interface AuthService extends Service {
    boolean loginFromCookies(Cookie[] cookies, UserDAO userDAO);
    boolean login(String login, String password);
    User getLoggedUser();
    boolean logout();
}
