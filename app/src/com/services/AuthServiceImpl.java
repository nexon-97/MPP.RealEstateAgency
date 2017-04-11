package com.services;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;
import com.model.User;

import javax.servlet.http.Cookie;
import java.util.Map;

public class AuthServiceImpl extends BaseService implements AuthService {
    private User loggedUser;

    private static String loginCookieName = "auth_login";
    private static String passwordCookieName = "auth_pass";

    public AuthServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.AuthorizationService, sharedResources);

        loginFromCookies();
    }

    @Override
    public boolean loginFromCookies() {
        UserDAO userDAO = new UserDAOImpl();
        Cookie[] cookies = getSharedResources().getCookies();

        String login = null;
        String password = null;

        if (cookies != null)
        {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();

                if (cookieName.equals(loginCookieName)) {
                    login = cookie.getValue();
                } else if (cookieName.equals(passwordCookieName)) {
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

        return false;
    }

    @Override
    public boolean login(String login, String password) {
        if (login == null || password == null) {
            return false;
        }

        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.getByLogin(login);
        if (user != null) {
            if (user.getPasswordHash().equals(password)) {
                setLoggedUser(user);

                // Put login data to cookies
                Cookie loginCookie = buildLoginCookie(loginCookieName, user.getLogin());
                Cookie passCookie = buildLoginCookie(passwordCookieName, user.getPasswordHash());

                ServiceSharedResources sharedResources = getSharedResources();
                sharedResources.addCookie(loginCookie);
                sharedResources.addCookie(passCookie);

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
        ServiceSharedResources sharedResources = getSharedResources();
        sharedResources.resetCookie(loginCookieName);
        sharedResources.resetCookie(passwordCookieName);

        return true;
    }

    private void setLoggedUser(User user) {
        loggedUser = user;

        // Put model data
        Map<String, Object> model = getSharedResources().getModel();
        model.put("user", user);
    }

    private Cookie buildLoginCookie(String name, String value) {
        int cookieDuration = 60 * 60 * 24 * 7;
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(cookieDuration);

        return cookie;
    }
}
