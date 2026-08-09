package service;

import dao.OfficerDAO;
import model.Officer;
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

public class OfficerService implements OfficerDAO {

    @Override
    public boolean registerOfficer(Connection connection, Officer officer) throws SQLException {

        String checkQuery = "SELECT officer_id FROM officers WHERE officer_id = ? OR phone = ? OR email = ?";
        String insertQuery = "INSERT INTO officers(officer_id,name,department,phone,email,active) VALUES(?,?,?,?,?,?)";

        if (!ValidationUtil.isValidId(officer.getOfficerId())) {
            System.out.println("Invalid Officer ID.");
            return false;
        }

        if (!ValidationUtil.isValidName(officer.getName())) {
            System.out.println("Invalid Name.");
            return false;
        }

        if (!ValidationUtil.isValidPhone(officer.getPhone())) {
            System.out.println("Invalid Phone Number.");
            return false;
        }

        if (!ValidationUtil.isValidEmail(officer.getEmail())) {
            System.out.println("Invalid Email.");
            return false;
        }

        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        checkStatement.setString(1, officer.getOfficerId());
        checkStatement.setString(2, officer.getPhone());
        checkStatement.setString(3, officer.getEmail());

        ResultSet resultSet = checkStatement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Officer already exists.");
            return false;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

        preparedStatement.setString(1, officer.getOfficerId());
        preparedStatement.setString(2, officer.getName());
        preparedStatement.setString(3, officer.getDepartment());
        preparedStatement.setString(4, officer.getPhone());
        preparedStatement.setString(5, officer.getEmail());
        preparedStatement.setBoolean(6, true);

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean registerOfficer(Officer officer) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return registerOfficer(connection, officer);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Officer> viewAllOfficers(Connection connection) throws SQLException {

        String query = "SELECT * FROM officers";

        List<Officer> officers = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            Officer officer = new Officer();

            officer.setOfficerId(resultSet.getString("officer_id"));
            officer.setName(resultSet.getString("name"));
            officer.setDepartment(resultSet.getString("department"));
            officer.setPhone(resultSet.getString("phone"));
            officer.setEmail(resultSet.getString("email"));
            officer.setActive(resultSet.getBoolean("active"));

            officers.add(officer);
        }

        return officers.stream()
                .sorted(Comparator.comparing(Officer::getOfficerId))
                .collect(Collectors.toList());
    }

    public List<Officer> viewAllOfficers() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewAllOfficers(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public Officer searchOfficerById(Connection connection, String officerId) throws SQLException {

        String query = "SELECT * FROM officers WHERE officer_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, officerId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {

            Officer officer = new Officer();

            officer.setOfficerId(resultSet.getString("officer_id"));
            officer.setName(resultSet.getString("name"));
            officer.setDepartment(resultSet.getString("department"));
            officer.setPhone(resultSet.getString("phone"));
            officer.setEmail(resultSet.getString("email"));
            officer.setActive(resultSet.getBoolean("active"));

            return officer;
        }

        return null;
    }

    public Officer searchOfficerById(String officerId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return searchOfficerById(connection, officerId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateOfficer(Connection connection, Officer officer) throws SQLException {

        String query = """
            UPDATE officers
            SET name = ?, department = ?, phone = ?, email = ?
            WHERE officer_id = ? AND active = TRUE
            """;

        if (!ValidationUtil.isValidName(officer.getName())) {
            System.out.println("Invalid Name.");
            return false;
        }

        if (!ValidationUtil.isValidPhone(officer.getPhone())) {
            System.out.println("Invalid Phone Number.");
            return false;
        }

        if (!ValidationUtil.isValidEmail(officer.getEmail())) {
            System.out.println("Invalid Email.");
            return false;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, officer.getName());
        preparedStatement.setString(2, officer.getDepartment());
        preparedStatement.setString(3, officer.getPhone());
        preparedStatement.setString(4, officer.getEmail());
        preparedStatement.setString(5, officer.getOfficerId());

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateOfficer(Officer officer) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return updateOfficer(connection, officer);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deactivateOfficer(Connection connection, String officerId) throws SQLException {

        String query = """
            UPDATE officers
            SET active = FALSE
            WHERE officer_id = ?
            """;

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, officerId);

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean deactivateOfficer(String officerId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return deactivateOfficer(connection, officerId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Officer> viewActiveOfficers(Connection connection) throws SQLException {

        String query = "SELECT * FROM officers WHERE active = TRUE";

        List<Officer> officers = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            Officer officer = new Officer();

            officer.setOfficerId(resultSet.getString("officer_id"));
            officer.setName(resultSet.getString("name"));
            officer.setDepartment(resultSet.getString("department"));
            officer.setPhone(resultSet.getString("phone"));
            officer.setEmail(resultSet.getString("email"));
            officer.setActive(resultSet.getBoolean("active"));

            officers.add(officer);
        }

        return officers.stream()
                .sorted(Comparator.comparing(Officer::getName))
                .collect(Collectors.toList());
    }

    public List<Officer> viewActiveOfficers() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewActiveOfficers(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}


