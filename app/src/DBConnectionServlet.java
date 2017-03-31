import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.*;

@WebServlet()
public class DBConnectionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GlobalConfig config = GlobalConfig.getInstance();
        UserDAO userInfo = new UserDAO();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + config.getDBSchema(),
                    config.getDBLogin(), config.getDBPassword());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `id`, `login`, `name`, `surname`, `email`, `phone` FROM `users`");

            resultSet.next();
            userInfo.id = resultSet.getInt(1);
            userInfo.login = resultSet.getString(2);
            userInfo.name = resultSet.getString(3);
            userInfo.surname = resultSet.getString(4);
            userInfo.email = resultSet.getString(5);
            userInfo.phoneNumber = resultSet.getString(6);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher("/db_connection.jsp");
        request.setAttribute("user", userInfo);
        rd.forward(request, response);
    }
}

