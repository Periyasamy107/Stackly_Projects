package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String HOST = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE = "complaint_management_db";
    private static final String URL = HOST + DATABASE;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver Not Found.");
            e.printStackTrace();
        }
    }

    public static Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static Connection getServerConnection() throws SQLException {
        return DriverManager.getConnection(HOST, USERNAME, PASSWORD);
    }

}
