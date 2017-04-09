package com.services;

import com.dao.UserDAO;
import com.model.User;

import javax.servlet.http.Cookie;

public class AuthServiceImpl implements AuthService {
    private User loggedUser;

    @Override
    public boolean loginFromCookies(Cookie[] cookies, UserDAO userDAO) {
        String login = null;
        String password = null;

        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();

            if (cookieName.equals("auth_login")) {
                login = cookie.getValue();
            } else if (cookieName.equals("auth_pass")) {
                password = cookie.getValue();
            }
        }

        if (login == null || password == null) {
            return false;
        }

        User databaseUser = userDAO.getByLogin(login);
        boolean loginSucceeded = (databaseUser != null) && (databaseUser.getPasswordHash().equals(password));
        if (loginSucceeded) {
            setLoggedUser(databaseUser);
        }

        return loginSucceeded;
    }

    @Override
    public boolean login(String login, String password, UserDAO userDAO) {
        if (login == null || password == null) {
            return false;
        }

        User user = userDAO.getByLogin(login);
        if (user != null) {
            if (user.getPasswordHash().equals(password)) {
                setLoggedUser(user);
                return true;
            }
        }

        return false;
    }

    @Override
    public User getLoggedUser() {
        return loggedUser;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public String getName() {
        return "AuthService";
    }

    private void setLoggedUser(User user) {
        loggedUser = user;
    }
}
