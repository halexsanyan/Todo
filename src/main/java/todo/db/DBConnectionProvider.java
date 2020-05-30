package todo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnectionProvider {
    private static DBConnectionProvider instance = new DBConnectionProvider();

    private Connection connection;

    private final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/todo";
    private final String USER_NAME = "root";
    private final String PASSWORD = "кщще";

    private DBConnectionProvider() {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBConnectionProvider getInstance() {
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

