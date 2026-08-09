package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "employee_leave_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    public static void initializeDatabase() {
        createDatabase();
        createTables();
    }

    private static void createDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
        try(Connection connection = DriverManager.getConnection(SERVER_URL, USER_NAME, PASSWORD);
            Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("\nDatabase created successfully.\n");

        } catch (SQLException ex) {

            System.out.println("\nDatabase creation failed.\n");
            System.out.println(ex.getMessage());

        }
    }

    public static void createTables() {
        try(Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement()) {

            createManagersTable(statement);
            createEmployeesTable(statement);
            createLeaveBalanceTable(statement);
            createLeaveRequestsTable(statement);

            System.out.println("\nAll tables created successfully.\n");

        } catch (SQLException ex) {

            System.out.println("\nTable creation failed.\n");
            System.out.println(ex.getMessage());

        }
    }

    private static void createManagersTable(Statement statement) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS managers (
                    manager_id varchar(10) PRIMARY KEY,
                    manager_name varchar(100) NOT NULL,
                    email varchar(100) NOT NULL UNIQUE,
                    phone varchar(10) NOT NULL,
                    department varchar(50) NOT NULL,
                    designation varchar(50) NOT NULL,
                    joining_date DATE NOT NULL,
                    status varchar(20) NOT NULL DEFAULT 'ACTIVE',
                    created_at DATE NOT NULL DEFAULT (CURRENT_DATE)
                )
                """;

        statement.executeUpdate(sql);
    }

    private static void createEmployeesTable(Statement statement)
            throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS employees (
                    employee_id VARCHAR(10) PRIMARY KEY,
                    employee_name VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    phone VARCHAR(10) NOT NULL,
                    department VARCHAR(50) NOT NULL,
                    designation VARCHAR(50) NOT NULL,
                    joining_date DATE NOT NULL,
                    manager_id VARCHAR(10) NOT NULL,
                    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                    created_at DATE NOT NULL DEFAULT (CURRENT_DATE),

                    CONSTRAINT fk_employee_manager
                    FOREIGN KEY (manager_id)
                    REFERENCES managers(manager_id)
                )
                """;

        statement.executeUpdate(sql);
    }

    private static void createLeaveBalanceTable(Statement statement)
            throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS leave_balance (
                    balance_id INT PRIMARY KEY AUTO_INCREMENT,
                    employee_id VARCHAR(10) NOT NULL,
                    leave_type VARCHAR(20) NOT NULL,
                    allocated_days INT NOT NULL,
                    used_days INT NOT NULL DEFAULT 0,

                    CONSTRAINT fk_balance_employee
                    FOREIGN KEY (employee_id)
                    REFERENCES employees(employee_id),

                    CONSTRAINT unique_employee_leave_type
                    UNIQUE (employee_id, leave_type)
                )
                """;

        statement.executeUpdate(sql);
    }

    private static void createLeaveRequestsTable(Statement statement)
            throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS leave_requests (
                    request_id INT PRIMARY KEY AUTO_INCREMENT,
                    employee_id VARCHAR(10) NOT NULL,
                    leave_type VARCHAR(20) NOT NULL,
                    start_date DATE NOT NULL,
                    end_date DATE NOT NULL,
                    number_of_days INT NOT NULL,
                    reason VARCHAR(255) NOT NULL,
                    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                    applied_date DATE NOT NULL DEFAULT (CURRENT_DATE),
                    approved_date DATE,
                    approved_by VARCHAR(10),

                    CONSTRAINT fk_request_employee
                    FOREIGN KEY (employee_id)
                    REFERENCES employees(employee_id),

                    CONSTRAINT fk_request_manager
                    FOREIGN KEY (approved_by)
                    REFERENCES managers(manager_id)
                )
                """;

        statement.executeUpdate(sql);
    }

}
