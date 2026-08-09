package dao;

import model.Employee;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public boolean employeeIdExists(String employeeId) {

        String sql = """
                SELECT employee_id
                FROM employees
                WHERE employee_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("\nError checking employee ID.\n");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean emailExists(String email) {

        String sql = """
                SELECT email
                FROM employees
                WHERE email = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("\nError checking employee email.\n");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean managerExists(String managerId) {

        String sql = """
                SELECT manager_id
                FROM managers
                WHERE manager_id = ?
                AND status = 'ACTIVE'
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, managerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("\nError checking manager.\n");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<>();

        String sql = """
            SELECT employee_id,
                   employee_name,
                   email,
                   phone,
                   department,
                   designation,
                   joining_date,
                   manager_id,
                   status,
                   created_at
            FROM employees
            ORDER BY employee_id
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee();

                employee.setEmployeeId(resultSet.getString("employee_id"));
                employee.setEmployeeName(resultSet.getString("employee_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setDesignation(resultSet.getString("designation"));
                employee.setJoiningDate(resultSet.getDate("joining_date").toLocalDate());
                employee.setManagerId(resultSet.getString("manager_id"));
                employee.setStatus(resultSet.getString("status"));
                employee.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("\nError retrieving employees.\n");
            System.out.println(e.getMessage());
        }
        return employees;
    }


    public Employee getEmployeeById(String employeeId) {

        String sql = """
            SELECT employee_id,
                   employee_name,
                   email,
                   phone,
                   department,
                   designation,
                   joining_date,
                   manager_id,
                   status,
                   created_at
            FROM employees
            WHERE employee_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    Employee employee = new Employee();

                    employee.setEmployeeId(resultSet.getString("employee_id"));
                    employee.setEmployeeName(resultSet.getString("employee_name"));
                    employee.setEmail(resultSet.getString("email"));
                    employee.setPhone(resultSet.getString("phone"));
                    employee.setDepartment(resultSet.getString("department"));
                    employee.setDesignation(resultSet.getString("designation"));
                    employee.setJoiningDate(resultSet.getDate("joining_date").toLocalDate());
                    employee.setManagerId(resultSet.getString("manager_id"));
                    employee.setStatus(resultSet.getString("status"));
                    employee.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

                    return employee;
                }
            }

        } catch (SQLException e) {
            System.out.println("\nError searching employee.\n");
            System.out.println(e.getMessage());
        }
        return null;
    }


    public boolean updateEmployee(Employee employee) {

        String sql = """
            UPDATE employees
            SET employee_name = ?,
                email = ?,
                phone = ?,
                department = ?,
                designation = ?,
                joining_date = ?,
                manager_id = ?,
                status = ?
            WHERE employee_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getPhone());
            preparedStatement.setString(4, employee.getDepartment());
            preparedStatement.setString(5, employee.getDesignation());
            preparedStatement.setDate(6, java.sql.Date.valueOf(employee.getJoiningDate()));
            preparedStatement.setString(7, employee.getManagerId());
            preparedStatement.setString(8, employee.getStatus());
            preparedStatement.setString(9, employee.getEmployeeId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("\nError updating employee.\n");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean deactiveEmployee(String employeeId) {

        String sql = """
            UPDATE employees
            SET status = 'INACTIVE'
            WHERE employee_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("\nError deactivating employee.\n");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean activeEmployeeExists(Connection connection, String employeeId) {

        String sql = """
            SELECT employee_id
            FROM employees
            WHERE employee_id = ?
            AND status = 'ACTIVE'
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("Error checking employee status.");
            System.out.println(e.getMessage());
        }
        return false;
    }



    public boolean registerEmployeeWithBalance(Employee employee) {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();

            connection.setAutoCommit(false);

            String employeeSql = """
                INSERT INTO employees
                (
                    employee_id,
                    employee_name,
                    email,
                    phone,
                    department,
                    designation,
                    joining_date,
                    manager_id,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

            try (PreparedStatement preparedStatement = connection.prepareStatement(employeeSql)) {

                preparedStatement.setString(1, employee.getEmployeeId());
                preparedStatement.setString(2, employee.getEmployeeName());
                preparedStatement.setString(3, employee.getEmail());
                preparedStatement.setString(4, employee.getPhone());
                preparedStatement.setString(5, employee.getDepartment());
                preparedStatement.setString(6, employee.getDesignation());
                preparedStatement.setDate(7, java.sql.Date.valueOf(employee.getJoiningDate()));
                preparedStatement.setString(8, employee.getManagerId());
                preparedStatement.setString(9, employee.getStatus());

                int rows = preparedStatement.executeUpdate();

                if (rows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            String balanceSql = """
                INSERT INTO leave_balance
                (
                    employee_id,
                    leave_type,
                    allocated_days,
                    used_days
                )
                VALUES (?, ?, ?, ?)
                """;

            try (PreparedStatement preparedStatement = connection.prepareStatement(balanceSql)) {

                insertBalance(preparedStatement, employee.getEmployeeId(), "CASUAL", 12);
                insertBalance(preparedStatement, employee.getEmployeeId(), "SICK", 10);
                insertBalance(preparedStatement, employee.getEmployeeId(), "EARNED", 15);
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.out.println("Employee registration transaction failed.");
            System.out.println(e.getMessage());
            return false;
        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }




    private void insertBalance(PreparedStatement preparedStatement, String employeeId, String leaveType, int allocatedDays)
            throws SQLException {

        preparedStatement.setString(1, employeeId);
        preparedStatement.setString(2, leaveType);
        preparedStatement.setInt(3, allocatedDays);
        preparedStatement.setInt(4, 0);

        preparedStatement.executeUpdate();
    }
}
