package services;

import dao.UserDAO;

import javax.servlet.http.HttpServletRequest;

public interface AuthService extends Service {
    boolean loginFromCookies(HttpServletRequest request);
    boolean login(String login, String password);
    UserDAO getLoggedUser();
    boolean logout();
}
