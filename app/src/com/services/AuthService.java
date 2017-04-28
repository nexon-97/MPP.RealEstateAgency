package com.services;

import com.model.User;

public interface AuthService {
    boolean loginFromCookies();
    boolean login(String login, String password);
    User getLoggedUser();
    boolean isUserLoggedIn();
    boolean logout();
}
