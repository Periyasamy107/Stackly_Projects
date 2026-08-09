package database;

import constant.ApplicationConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHotel {

    private static final String SERVER_URL = ApplicationConstants.SERVER_URL;
    private static final String DATABASE_URL = ApplicationConstants.DATABASE_URL;
    private static final String USER_NAME = ApplicationConstants.USERNAME;
    private static final String PASSWORD = ApplicationConstants.PASSWORD;

    public static Connection getServerConnection() throws SQLException {
        return DriverManager.getConnection(SERVER_URL, USER_NAME, PASSWORD);
    }

    public static Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USER_NAME, PASSWORD);
    }

}
