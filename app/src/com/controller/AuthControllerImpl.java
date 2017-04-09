package com.controller;

import com.dao.UserDAO;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.services.AuthService;
import com.services.ServiceManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthControllerImpl implements com.controller.AuthController {
    @Autowired
    ApplicationContext context;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    protected ModelAndView visitAuthorizationForm() {
        Map<String, Object> model = new HashMap<>();
        UserDAO userDAO = context.getBean(UserDAO.class);

        AuthService authService = (AuthService)ServiceManager.getInstance().getService("AuthService");
        Cookie[] cookies = request.getCookies();
        if (authService.loginFromCookies(cookies, userDAO)) {
            User loggedUser = authService.getLoggedUser();

            model.put("loginSucceeded", true);
            model.put("login", loggedUser.getLogin());
            return new ModelAndView("auth_result", model);
        }

        return new ModelAndView("auth", model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth")
    protected ModelAndView authorize() {
        //AuthService authService = (AuthService)ServiceManager.getInstance(request).getService("AuthService");
        //UserDAOImpl loggedUser = authService.getLoggedUser();

        /*if (loggedUser != null) {
            System.out.println("Person already logged in!");
            response.sendRedirect("/");
        } else {
            RequestDispatcher rd;
            rd = request.getRequestDispatcher("views/auth.jsp");
            rd.forward(request, response);
        }*/

        Map<String, ?> model = new HashMap<>();
        return new ModelAndView("auth_result", model);
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd;
        Statement statement = getDbContext();

        if (statement != null) {
            try
            {
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                String query = String.format("SELECT COUNT(*) FROM `users` WHERE `login` = '%s' and `password_hash` = '%s'", login, password);

                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                int recordsCount = resultSet.getInt(1);

                boolean loginSucceeded = (recordsCount == 1);

                rd = request.getRequestDispatcher("views/auth_result.jsp");
                request.setAttribute("loginSucceeded", loginSucceeded);
                if (loginSucceeded) {
                    request.setAttribute("login", login);

                    Cookie authLoginCookie = new Cookie("auth_login", login);
                    Cookie authPasswordCookie = new Cookie("auth_pass", password);
                    int cookieMaxAge = 60 * 60 * 24 * 7;

                    authLoginCookie.setMaxAge(cookieMaxAge);
                    authPasswordCookie.setMaxAge(cookieMaxAge);

                    response.addCookie(authLoginCookie);
                    response.addCookie(authPasswordCookie);
                }
            } catch (SQLException e) {
                rd = request.getRequestDispatcher("views/error_message.jsp");
                request.setAttribute("msg", e.getMessage());
            }
        } else {
            rd = request.getRequestDispatcher("views/error_message.jsp");
            request.setAttribute("msg", "Failed to establish database connection");
        }

        rd.forward(request, response);
    }*/

    @Override
    public boolean checkUser(String login) {
        return false;
    }

    @Override
    public boolean login(String login, String password) {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }
}
