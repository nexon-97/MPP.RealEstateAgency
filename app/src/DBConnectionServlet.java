import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/dbConnection")
public class DBConnectionServlet extends HttpServlet{
    private static String user = "root";
    private static String pass = "123456789";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*try {*/
            DBConnectionExample.kek();
           /* Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/real_estate_agency",
                    user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `example_table` WHERE `id` > 5");

            while(resultSet.next()){
                System.out.print(resultSet.getInt(2) + "  ");
                System.out.println(resultSet.getString(3));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}

