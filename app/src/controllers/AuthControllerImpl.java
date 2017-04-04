package controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import test.GlobalConfig;
import java.sql.*;

@WebServlet
public class AuthControllerImpl extends HttpServlet implements AuthController {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd;

        rd = request.getRequestDispatcher("views/auth.jsp");
        rd.forward(request, response);
    }

    @Override
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
    }

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

    private Statement getDbContext() {
        GlobalConfig config = GlobalConfig.getInstance();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + config.getDBSchema(),
                    config.getDBLogin(), config.getDBPassword());
            Statement statement = connection.createStatement();

            return statement;
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }
}
