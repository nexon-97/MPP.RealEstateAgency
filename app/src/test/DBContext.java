package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBContext {
    private static DBContext instance = null;

    private Statement dbStatement;

    protected DBContext() {
        Init();
    }

    protected void Init() {
        GlobalConfig config = GlobalConfig.getInstance();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + config.getDBSchema(),
                    config.getDBLogin(), config.getDBPassword());
            dbStatement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Statement getStatement() {
        return dbStatement;
    }

    public static DBContext getInstance() {
        if (instance == null) {
            instance = new DBContext();
        }

        return instance;
    }
}
