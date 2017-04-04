import dao.UserDAO;
import test.GlobalConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet()
public class DBConnectionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd;
        GlobalConfig config = GlobalConfig.getInstance();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + config.getDBSchema(),
                    config.getDBLogin(), config.getDBPassword());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `id`, `login`, `name`, `surname`, `email`, `phone` FROM `users`");

            List<UserDAO> usersList = new ArrayList<>();
            while (resultSet.next())
            {
                UserDAO userInfo = new UserDAO();
                userInfo.id = resultSet.getInt(1);
                userInfo.login = resultSet.getString(2);
                userInfo.name = resultSet.getString(3);
                userInfo.surname = resultSet.getString(4);
                userInfo.email = resultSet.getString(5);
                userInfo.phoneNumber = resultSet.getString(6);

                usersList.add(userInfo);
            }

            rd = request.getRequestDispatcher("views/db_connection.jsp");
            request.setAttribute("users", usersList);
        } catch (SQLException | ClassNotFoundException e) {
            rd = request.getRequestDispatcher("views/error_message.jsp");
            request.setAttribute("msg", "Failed to connect to database!");
        }

        rd.forward(request, response);
    }
}

