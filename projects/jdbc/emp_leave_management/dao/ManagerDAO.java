package dao;

import model.Manager;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {

    public boolean managerIdExists(String managerId) {
        String sql = """
                SELECT manager_id from managers WHERE manager_id = ?
                """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, managerId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException exception) {

            System.out.println("\nError checking with manager id.\n");
            System.out.println(exception.getMessage());

        }
        return false;
    }


    public boolean emailExists(String email) {
        String sql = """
                SELECT email FROM managers WHERE email = ?
                """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            System.out.println("\nError checking with manager email.\n");
            System.out.println(ex.getMessage());

        }
        return false;
    }


    public boolean addManager (Manager manager) {
        String sql = """
                INSERT INTO managers ( manager_id, manager_name, email, phone, department, designation, joining_date, status )
                 values ( ?, ?, ?, ?, ?, ?, ?, ? )
                """;

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, manager.getManagerId());
            preparedStatement.setString(2, manager.getManagerName());
            preparedStatement.setString(3, manager.getEmail());
            preparedStatement.setString(4, manager.getPhone());
            preparedStatement.setString(5, manager.getDepartment());
            preparedStatement.setString(6, manager.getDesignation());
            preparedStatement.setDate(7, Date.valueOf(manager.getJoiningDate()));
            preparedStatement.setString(8, manager.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException sqlException) {
            System.out.println("\nError while registering the manager details.\n");
            System.out.println(sqlException.getMessage());

        }
        return false;
    }


    public List<Manager> getAllManagers() {

        List<Manager> managers = new ArrayList<>();

        String sql = """
            SELECT manager_id,
                   manager_name,
                   email,
                   phone,
                   department,
                   designation,
                   joining_date,
                   status,
                   created_at
            FROM managers
            ORDER BY manager_id
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Manager manager = new Manager();

                manager.setManagerId(resultSet.getString("manager_id"));
                manager.setManagerName(resultSet.getString("manager_name"));
                manager.setEmail(resultSet.getString("email"));
                manager.setPhone(resultSet.getString("phone"));
                manager.setDepartment(resultSet.getString("department"));
                manager.setDesignation(resultSet.getString("designation"));
                manager.setJoiningDate(resultSet.getDate("joining_date").toLocalDate());
                manager.setStatus(resultSet.getString("status"));
                manager.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

                managers.add(manager);
            }
        } catch (SQLException e) {
            System.out.println("\nError retrieving managers.\n");
            System.out.println(e.getMessage());
        }
        return managers;
    }

    public Manager getManagerById(String managerId) {

        String sql = """
            SELECT manager_id,
                   manager_name,
                   email,
                   phone,
                   department,
                   designation,
                   joining_date,
                   status,
                   created_at
            FROM managers
            WHERE manager_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, managerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    Manager manager = new Manager();

                    manager.setManagerId(resultSet.getString("manager_id"));
                    manager.setManagerName(resultSet.getString("manager_name"));
                    manager.setEmail(resultSet.getString("email"));
                    manager.setPhone(resultSet.getString("phone"));
                    manager.setDepartment(resultSet.getString("department"));
                    manager.setDesignation(resultSet.getString("designation"));
                    manager.setJoiningDate(resultSet.getDate("joining_date").toLocalDate());
                    manager.setStatus(resultSet.getString("status"));
                    manager.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

                    return manager;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching manager.");
            System.out.println(e.getMessage());
        }
        return null;
    }


    public boolean updateManager(Manager manager) {

        String sql = """
            UPDATE managers
            SET manager_name = ?,
                email = ?,
                phone = ?,
                department = ?,
                designation = ?,
                joining_date = ?,
                status = ?
            WHERE manager_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, manager.getManagerName());
            preparedStatement.setString(2, manager.getEmail());
            preparedStatement.setString(3, manager.getPhone());
            preparedStatement.setString(4, manager.getDepartment());
            preparedStatement.setString(5, manager.getDesignation());
            preparedStatement.setDate(6, java.sql.Date.valueOf(manager.getJoiningDate()));
            preparedStatement.setString(7, manager.getStatus());
            preparedStatement.setString(8, manager.getManagerId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating manager.");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean deactivatedManager(String managerId) {

        String sql = """
            UPDATE managers
            SET status = 'INACTIVE'
            WHERE manager_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, managerId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deactivating manager.");
            System.out.println(e.getMessage());
        }
        return false;
    }

}
