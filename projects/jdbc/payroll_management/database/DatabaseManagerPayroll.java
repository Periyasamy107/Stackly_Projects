package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagerPayroll {

    private static final String SERVER_URL =
            "jdbc:mysql://localhost:3306/";

    private static final String DATABASE_NAME =
            "payroll_management";

    private static final String USERNAME =
            "root";

    private static final String PASSWORD =
            "root";

    private static final String DATABASE_URL =
            SERVER_URL + DATABASE_NAME;


    // Create database if it does not exist
    public static void createPayrollDatabase() {

        String sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;

        try (Connection connection = DriverManager.getConnection(
                SERVER_URL,
                USERNAME,
                PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);

            System.out.println("Database created/verified successfully.");

        } catch (SQLException e) {

            System.out.println("Database creation failed.");
            e.printStackTrace();
        }
    }


    // Get connection to payroll_management database
    public static Connection getPayrollConnection() throws SQLException {

        return DriverManager.getConnection(
                DATABASE_URL,
                USERNAME,
                PASSWORD);
    }


    // Create all required tables
    public static void createPayrollTables() {

        String employeeTable = """
            CREATE TABLE IF NOT EXISTS employeePS (
                employee_id VARCHAR(20) PRIMARY KEY,
                employee_name VARCHAR(100) NOT NULL,
                department VARCHAR(50) NOT NULL,
                designation VARCHAR(50) NOT NULL,
                email VARCHAR(100),
                phone VARCHAR(20),
                basic_salary DECIMAL(10,2) NOT NULL,
                hra DECIMAL(10,2) DEFAULT 0,
                allowance DECIMAL(10,2) DEFAULT 0,
                status VARCHAR(20) DEFAULT 'ACTIVE',
                joining_date DATE NOT NULL
            )
            """;


        String salaryTable = """
            CREATE TABLE IF NOT EXISTS salaries (
                salary_id INT AUTO_INCREMENT PRIMARY KEY,
                employee_id VARCHAR(20) NOT NULL,
                basic_salary DECIMAL(10,2) NOT NULL,
                hra DECIMAL(10,2) DEFAULT 0,
                allowance DECIMAL(10,2) DEFAULT 0,
                gross_salary DECIMAL(10,2) NOT NULL,
                effective_date DATE NOT NULL,

                CONSTRAINT fk_salary_employee
                FOREIGN KEY (employee_id)
                REFERENCES employeePS(employee_id)
            )
            """;


        String payrollTable = """
            CREATE TABLE IF NOT EXISTS payroll (
                payroll_id INT AUTO_INCREMENT PRIMARY KEY,
                employee_id VARCHAR(20) NOT NULL,
                pay_month DATE NOT NULL,
                basic_salary DECIMAL(10,2) NOT NULL,
                hra DECIMAL(10,2) DEFAULT 0,
                allowance DECIMAL(10,2) DEFAULT 0,
                gross_salary DECIMAL(10,2) NOT NULL,
                tax DECIMAL(10,2) DEFAULT 0,
                other_deduction DECIMAL(10,2) DEFAULT 0,
                total_deduction DECIMAL(10,2) DEFAULT 0,
                net_salary DECIMAL(10,2) NOT NULL,
                payment_status VARCHAR(20) DEFAULT 'PENDING',
                generated_date DATE NOT NULL,

                CONSTRAINT fk_payroll_employee
                FOREIGN KEY (employee_id)
                REFERENCES employeePS(employee_id)
            )
            """;


        try (Connection connection = getPayrollConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(employeeTable);

            statement.executeUpdate(salaryTable);

            statement.executeUpdate(payrollTable);

            System.out.println("All tables created/verified successfully.");

        } catch (SQLException e) {

            System.out.println("Table creation failed.");
            e.printStackTrace();
        }
    }
}
