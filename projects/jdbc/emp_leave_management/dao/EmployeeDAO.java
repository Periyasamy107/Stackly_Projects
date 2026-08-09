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
                FROM EmployeeS
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
                FROM EmployeeS
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

        List<Employee> EmployeeS = new ArrayList<>();

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
            FROM EmployeeS
            ORDER BY employee_id
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee Employee = new Employee();

                Employee.setEmployeeId(resultSet.getString("employee_id"));
                Employee.setEmployeeName(resultSet.getString("employee_name"));
                Employee.setEmail(resultSet.getString("email"));
                Employee.setPhone(resultSet.getString("phone"));
                Employee.setDepartment(resultSet.getString("department"));
                Employee.setDesignation(resultSet.getString("designation"));
                Employee.setJoiningDate(resultSet.getDate("joining_date").toLocalDate());
                Employee.setManagerId(resultSet.getString("manager_id"));
                Employee.setStatus(resultSet.getString("status"));
                Employee.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

                EmployeeS.add(Employee);
            }
        } catch (SQLException e) {
            System.out.println("\nError retrieving EmployeeS.\n");
            System.out.println(e.getMessage());
        }
        return EmployeeS;
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
            FROM EmployeeS
            WHERE employee_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    Employee Employee = new Employee();

                    Employee.setEmployeeId(resultSet.getString("employee_id"));
                    Employee.setEmployeeName(resultSet.getString("employee_name"));
                    Employee.setEmail(resultSet.getString("email"));
                    Employee.setPhone(resultSet.getString("phone"));
                    Employee.setDepartment(resultSet.getString("department"));
                    Employee.setDesignation(resultSet.getString("designation"));
                    Employee.setJoiningDate(resultSet.getDate("joining_date").toLocalDate());
                    Employee.setManagerId(resultSet.getString("manager_id"));
                    Employee.setStatus(resultSet.getString("status"));
                    Employee.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

                    return Employee;
                }
            }

        } catch (SQLException e) {
            System.out.println("\nError searching employee.\n");
            System.out.println(e.getMessage());
        }
        return null;
    }


    public boolean updateEmployee(Employee Employee) {

        String sql = """
            UPDATE EmployeeS
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

            preparedStatement.setString(1, Employee.getEmployeeName());
            preparedStatement.setString(2, Employee.getEmail());
            preparedStatement.setString(3, Employee.getPhone());
            preparedStatement.setString(4, Employee.getDepartment());
            preparedStatement.setString(5, Employee.getDesignation());
            preparedStatement.setDate(6, java.sql.Date.valueOf(Employee.getJoiningDate()));
            preparedStatement.setString(7, Employee.getManagerId());
            preparedStatement.setString(8, Employee.getStatus());
            preparedStatement.setString(9, Employee.getEmployeeId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("\nError updating Employee.\n");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean deactiveEmployee(String employeeId) {

        String sql = """
            UPDATE EmployeeS
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
            FROM EmployeeS
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



    public boolean registerEmployeeWithBalance(Employee Employee) {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();

            connection.setAutoCommit(false);

            String employeeSql = """
                INSERT INTO EmployeeS
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

                preparedStatement.setString(1, Employee.getEmployeeId());
                preparedStatement.setString(2, Employee.getEmployeeName());
                preparedStatement.setString(3, Employee.getEmail());
                preparedStatement.setString(4, Employee.getPhone());
                preparedStatement.setString(5, Employee.getDepartment());
                preparedStatement.setString(6, Employee.getDesignation());
                preparedStatement.setDate(7, java.sql.Date.valueOf(Employee.getJoiningDate()));
                preparedStatement.setString(8, Employee.getManagerId());
                preparedStatement.setString(9, Employee.getStatus());

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

                insertBalance(preparedStatement, Employee.getEmployeeId(), "CASUAL", 12);
                insertBalance(preparedStatement, Employee.getEmployeeId(), "SICK", 10);
                insertBalance(preparedStatement, Employee.getEmployeeId(), "EARNED", 15);
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
