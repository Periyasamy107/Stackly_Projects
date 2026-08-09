package dao;

import model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    boolean registerUser(Connection connection, User user) throws SQLException;

    List<User> viewAllUsers(Connection connection) throws SQLException;

    User searchUserById(Connection connection, String userId) throws SQLException;

    boolean updateUser(Connection connection, User user) throws SQLException;

    boolean deactivateUser(Connection connection, String userId) throws SQLException;

    List<User> viewActiveUsers(Connection connection) throws SQLException;

    List<User> viewDeactivatedUsers(Connection connection) throws SQLException;

}
