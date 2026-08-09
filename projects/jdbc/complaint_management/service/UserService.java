package service;

import dao.UserDAO;
import exception.DuplicateUserException;
import model.User;
import util.ValidationUtil;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements UserDAO {

    @Override
    public boolean registerUser(Connection connection, User user) throws SQLException {

        String checkQuery = "SELECT user_id FROM users WHERE user_id = ? OR phone = ? OR email = ?";
        String insertQuery = "INSERT INTO users(user_id,name,phone,email,address,active) VALUES(?,?,?,?,?,?)";

        try {

            if (!ValidationUtil.isValidId(user.getUserId())) {
                System.out.println("Invalid User ID.");
                return false;
            }

            if (!ValidationUtil.isValidName(user.getName())) {
                System.out.println("Invalid Name.");
                return false;
            }

            if (!ValidationUtil.isValidPhone(user.getPhone())) {
                System.out.println("Invalid Phone Number.");
                return false;
            }

            if (!ValidationUtil.isValidEmail(user.getEmail())) {
                System.out.println("Invalid Email.");
                return false;
            }

            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, user.getUserId());
            checkStatement.setString(2, user.getPhone());
            checkStatement.setString(3, user.getEmail());

            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                throw new DuplicateUserException("User already exists.");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setBoolean(6, true);

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (DuplicateUserException e) {

            System.out.println(e.getMessage());

        }

        return false;
    }

    public boolean registerUser(User user) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return registerUser(connection, user);

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<User> viewAllUsers(Connection connection) throws SQLException {

        String query = "SELECT * FROM users";

        List<User> users = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            User user = new User();

            user.setUserId(resultSet.getString("user_id"));
            user.setName(resultSet.getString("name"));
            user.setPhone(resultSet.getString("phone"));
            user.setEmail(resultSet.getString("email"));
            user.setAddress(resultSet.getString("address"));
            user.setActive(resultSet.getBoolean("active"));

            users.add(user);
        }

        return users.stream()
                .sorted(Comparator.comparing(User::getUserId))
                .collect(Collectors.toList());
    }

    public List<User> viewAllUsers() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewAllUsers(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    @Override
    public User searchUserById(Connection connection, String userId) throws SQLException {

        String query = "SELECT * FROM users WHERE user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {

            User user = new User();

            user.setUserId(resultSet.getString("user_id"));
            user.setName(resultSet.getString("name"));
            user.setPhone(resultSet.getString("phone"));
            user.setEmail(resultSet.getString("email"));
            user.setAddress(resultSet.getString("address"));
            user.setActive(resultSet.getBoolean("active"));

            return user;
        }

        return null;
    }

    public User searchUserById(String userId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return searchUserById(connection, userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateUser(Connection connection, User user) throws SQLException {

        String query = """
            UPDATE users
            SET name = ?, phone = ?, email = ?, address = ?
            WHERE user_id = ? AND active = TRUE
            """;

        if (!ValidationUtil.isValidName(user.getName())) {
            System.out.println("Invalid Name.");
            return false;
        }

        if (!ValidationUtil.isValidPhone(user.getPhone())) {
            System.out.println("Invalid Phone Number.");
            return false;
        }

        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            System.out.println("Invalid Email.");
            return false;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPhone());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getAddress());
        preparedStatement.setString(5, user.getUserId());

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateUser(User user) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return updateUser(connection, user);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deactivateUser(Connection connection, String userId) throws SQLException {

        String query = """
            UPDATE users
            SET active = FALSE
            WHERE user_id = ?
            """;

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userId);

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean deactivateUser(String userId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return deactivateUser(connection, userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<User> viewActiveUsers(Connection connection) throws SQLException {

        String query = "SELECT * FROM users WHERE active = TRUE";

        List<User> users = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            User user = new User();

            user.setUserId(resultSet.getString("user_id"));
            user.setName(resultSet.getString("name"));
            user.setPhone(resultSet.getString("phone"));
            user.setEmail(resultSet.getString("email"));
            user.setAddress(resultSet.getString("address"));
            user.setActive(resultSet.getBoolean("active"));

            users.add(user);
        }

        return users.stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    public List<User> viewActiveUsers() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewActiveUsers(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<User> viewDeactivatedUsers(Connection connection) throws SQLException {

        String query = "SELECT * FROM users WHERE active = FALSE";

        List<User> users = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            User user = new User();

            user.setUserId(resultSet.getString("user_id"));
            user.setName(resultSet.getString("name"));
            user.setPhone(resultSet.getString("phone"));
            user.setEmail(resultSet.getString("email"));
            user.setAddress(resultSet.getString("address"));
            user.setActive(resultSet.getBoolean("active"));

            users.add(user);
        }

        return users.stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    public List<User> viewDeactivatedUsers() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewDeactivatedUsers(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}