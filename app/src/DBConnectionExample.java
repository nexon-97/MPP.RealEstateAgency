import java.sql.*;

public class DBConnectionExample {
    private static String user = "root";
    private static String pass = "123456789";
    public static void kek() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/real_estate_agency",
                    user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `example_table` WHERE `id` > 5");

            while(resultSet.next()){
                System.out.print(resultSet.getInt(2) + "  ");
                System.out.println(resultSet.getString(3));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        kek();
    }
}
