package com.services.impl;

import com.dao.UserDAO;
import com.dao.impl.UserDAOImpl;
import com.model.User;
import com.services.AuthService;
import com.services.shared.*;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                    try {
                        login = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else if (cookieName.equals(passwordCookieName)) {
                    try {
                        password = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (login == null || password == null) {
                return false;
            }

            User databaseUser = userDAO.getByLogin(login);
            boolean loginSucceeded = (databaseUser != null) && (databaseUser.getPasswordHash().equals(password));//.equals(getPasswordHash(password)));//
            if (loginSucceeded) {
                setLoggedUser(databaseUser);
            }

            return loginSucceeded;
        }

        return false;
    }

    @Override
    public boolean login(String login, String password) {
        if (login != null || password != null) {
            UserDAO userDAO = new UserDAOImpl();

            User user = userDAO.getByLogin(login);
            if (user != null) {
                if (user.getPasswordHash().equals(getPasswordHash(password))) {
                    setLoggedUser(user);

                    // Put login data to cookies
                    Cookie loginCookie = buildLoginCookie(loginCookieName, user.getLogin());
                    Cookie passCookie = buildLoginCookie(passwordCookieName, getPasswordHash(password));//password);//;

                    ServiceSharedResources sharedResources = getSharedResources();
                    sharedResources.addCookie(loginCookie);
                    sharedResources.addCookie(passCookie);

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public User getLoggedUser() {
        return loggedUser;
    }

    @Override
    public boolean isUserLoggedIn() {
        return this.loggedUser != null;
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
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
            cookie.setMaxAge(cookieDuration);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return cookie;
    }

    private static String getPasswordHash(String password){
        String passwordHash = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            passwordHash = new String(md5.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHash;
    }


}
