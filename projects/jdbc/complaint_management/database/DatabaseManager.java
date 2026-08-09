package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    public static void initializeDatabase() {
        createDatabase();
        createUsersTable();
        createOfficersTable();
        createComplaintsTable();

        System.out.println("\n========================================");
        System.out.println("   Complaint Management Database Ready.");
        System.out.println("========================================\n");
    }


    private static void createDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS complaint_management_db";
        try(Connection connection = DBConnectionHotel.getServerConnection();
            Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("\nDatabase Created Successfully.\n");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    user_id varchar(20) PRIMARY KEY,
                    name varchar(50) NOT NULL,
                    phone varchar(10) UNIQUE NOT NULL,
                    email varchar(100) UNIQUE,
                    address varchar(100),
                    active BOOLEAN DEFAULT TRUE
                )
                """;
        executeTableCreation(sql, "users");
    }


    private static void createOfficersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS officers (
                    officer_id VARCHAR(20) PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    department VARCHAR(100) NOT NULL,
                    phone VARCHAR(10) UNIQUE,
                    email VARCHAR(100) UNIQUE,
                    active BOOLEAN DEFAULT TRUE
                )
                """;
        executeTableCreation(sql, "officers");
    }


    private static void createComplaintsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS complaints (
                    complaint_id VARCHAR(20) PRIMARY KEY,
                    user_id VARCHAR(20) NOT NULL,
                    officer_id VARCHAR(20),
                    category VARCHAR(50) NOT NULL,
                    description TEXT NOT NULL,
                    complaint_date DATE NOT NULL,
                    status VARCHAR(30) NOT NULL,
                    resolution TEXT,
                    resolved_date DATE,

                    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(user_id),
                    CONSTRAINT fk_officer FOREIGN KEY(officer_id) REFERENCES officers(officer_id)
                )
                """;
        executeTableCreation(sql, "Complaints");
    }

    private static void executeTableCreation(String sql, String tableName) {
        try(Connection connection = DBConnectionHotel.getDatabaseConnection();
            Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println(tableName+" table created successfully.\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
