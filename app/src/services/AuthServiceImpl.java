package services;

import dao.UserDAO;
import test.DBContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthServiceImpl implements AuthService {
    private UserDAO loggedUser;
    private HttpServletRequest request;

    public AuthServiceImpl(HttpServletRequest request) {
        this.request = request;

        // Try to log in from cookies first
        loginFromCookies(request);
    }

    @Override
    public boolean loginFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

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

        Statement dbStatement = DBContext.getInstance().getStatement();
        try
        {
            String query = String.format("SELECT COUNT(*) FROM `users` WHERE `login` = '%s' and `password_hash` = '%s'", login, password);

            ResultSet resultSet = dbStatement.executeQuery(query);
            resultSet.next();
            int recordsCount = resultSet.getInt(1);

            boolean loginSucceeded = (recordsCount == 1);
            if (loginSucceeded) {
                UserDAO user = new UserDAO();
                user.login = login;

                setLoggedUser(user);
            }

            return loginSucceeded;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean login(String login, String password) {
        return false;
    }

    @Override
    public UserDAO getLoggedUser() {
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

    private void setLoggedUser(UserDAO user) {
        loggedUser = user;

        if (loggedUser != null) {
            request.setAttribute("loginSucceeded", true);
            request.setAttribute("login", user.getLogin());
        }
    }
}
