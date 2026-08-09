package service;

import dao.ComplaintDAO;
import database.DBConnection;
import enums.ComplaintCategory;
import enums.ComplaintStatus;
import model.Complaint;
import util.ValidationUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ComplaintService implements ComplaintDAO {

    @Override
    public boolean registerComplaint(Connection connection, Complaint complaint) throws SQLException {

        String checkQuery = "SELECT complaint_id FROM complaints WHERE complaint_id = ?";
        String insertQuery = """
                INSERT INTO complaints
                (complaint_id,user_id,officer_id,category,description,complaint_date,status,resolution,resolved_date)
                VALUES(?,?,?,?,?,?,?,?,?)
                """;

        if (!ValidationUtil.isValidId(complaint.getComplaintId())) {
            System.out.println("Invalid Complaint ID.");
            return false;
        }

        if (!ValidationUtil.isValidDescription(complaint.getDescription())) {
            System.out.println("Invalid Description.");
            return false;
        }

        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        checkStatement.setString(1, complaint.getComplaintId());

        ResultSet rs = checkStatement.executeQuery();

        if (rs.next()) {
            System.out.println("Complaint already exists.");
            return false;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

        preparedStatement.setString(1, complaint.getComplaintId());
        preparedStatement.setString(2, complaint.getUserId());
        preparedStatement.setString(3, complaint.getOfficerId());
        preparedStatement.setString(4, complaint.getCategory().name());
        preparedStatement.setString(5, complaint.getDescription());
        preparedStatement.setDate(6, Date.valueOf(complaint.getComplaintDate()));
        preparedStatement.setString(7, complaint.getStatus().name());
        preparedStatement.setString(8, complaint.getResolution());

        if (complaint.getResolvedDate() != null) {
            preparedStatement.setDate(9, Date.valueOf(complaint.getResolvedDate()));
        } else {
            preparedStatement.setDate(9, null);
        }

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean registerComplaint(Complaint complaint) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return registerComplaint(connection, complaint);

        } catch(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            System.out.println("User not found");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Complaint> viewAllComplaints(Connection connection) throws SQLException {

        String query = "SELECT * FROM complaints";

        List<Complaint> complaints = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            Complaint complaint = new Complaint();

            complaint.setComplaintId(resultSet.getString("complaint_id"));
            complaint.setUserId(resultSet.getString("user_id"));
            complaint.setOfficerId(resultSet.getString("officer_id"));
            complaint.setCategory(ComplaintCategory.valueOf(resultSet.getString("category")));
            complaint.setDescription(resultSet.getString("description"));
            complaint.setComplaintDate(resultSet.getDate("complaint_date").toLocalDate());
            complaint.setStatus(ComplaintStatus.valueOf(resultSet.getString("status")));
            complaint.setResolution(resultSet.getString("resolution"));

            if (resultSet.getDate("resolved_date") != null) {
                complaint.setResolvedDate(resultSet.getDate("resolved_date").toLocalDate());
            }

            complaints.add(complaint);
        }

        return complaints.stream()
                .sorted(Comparator.comparing(Complaint::getComplaintDate))
                .collect(Collectors.toList());
    }

    public List<Complaint> viewAllComplaints() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewAllComplaints(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public Complaint searchComplaintById(Connection connection, String complaintId) throws SQLException {

        String query = "SELECT * FROM complaints WHERE complaint_id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, complaintId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {

            Complaint complaint = new Complaint();

            complaint.setComplaintId(resultSet.getString("complaint_id"));
            complaint.setUserId(resultSet.getString("user_id"));
            complaint.setOfficerId(resultSet.getString("officer_id"));
            complaint.setCategory(ComplaintCategory.valueOf(resultSet.getString("category")));
            complaint.setDescription(resultSet.getString("description"));
            complaint.setComplaintDate(resultSet.getDate("complaint_date").toLocalDate());
            complaint.setStatus(ComplaintStatus.valueOf(resultSet.getString("status")));
            complaint.setResolution(resultSet.getString("resolution"));

            if (resultSet.getDate("resolved_date") != null) {
                complaint.setResolvedDate(resultSet.getDate("resolved_date").toLocalDate());
            }

            return complaint;
        }

        return null;
    }

    public Complaint searchComplaintById(String complaintId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return searchComplaintById(connection, complaintId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateComplaint(Connection connection, Complaint complaint) throws SQLException {

        String query = """
            UPDATE complaints
            SET
                user_id = ?,
                officer_id = ?,
                category = ?,
                description = ?,
                complaint_date = ?,
                status = ?,
                resolution = ?,
                resolved_date = ?
            WHERE complaint_id = ?
            """;

        if (!ValidationUtil.isValidDescription(complaint.getDescription())) {
            System.out.println("Invalid Description.");
            return false;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, complaint.getUserId());
        preparedStatement.setString(2, complaint.getOfficerId());
        preparedStatement.setString(3, complaint.getCategory().name());
        preparedStatement.setString(4, complaint.getDescription());
        preparedStatement.setDate(5, Date.valueOf(complaint.getComplaintDate()));
        preparedStatement.setString(6, complaint.getStatus().name());
        preparedStatement.setString(7, complaint.getResolution());

        if (complaint.getResolvedDate() != null) {
            preparedStatement.setDate(8, Date.valueOf(complaint.getResolvedDate()));
        } else {
            preparedStatement.setNull(8, java.sql.Types.DATE);
        }

        preparedStatement.setString(9, complaint.getComplaintId());

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateComplaint(Complaint complaint) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return updateComplaint(connection, complaint);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteComplaint(Connection connection, String complaintId) throws SQLException {

        String query = "DELETE FROM complaints WHERE complaint_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, complaintId);

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean deleteComplaint(String complaintId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return deleteComplaint(connection, complaintId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Complaint> viewComplaintsByUser(Connection connection, String userId) throws SQLException {

        String query = "SELECT * FROM complaints WHERE user_id = ?";

        List<Complaint> complaints = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            Complaint complaint = new Complaint();

            complaint.setComplaintId(resultSet.getString("complaint_id"));
            complaint.setUserId(resultSet.getString("user_id"));
            complaint.setOfficerId(resultSet.getString("officer_id"));
            complaint.setCategory(ComplaintCategory.valueOf(resultSet.getString("category")));
            complaint.setDescription(resultSet.getString("description"));
            complaint.setComplaintDate(resultSet.getDate("complaint_date").toLocalDate());
            complaint.setStatus(ComplaintStatus.valueOf(resultSet.getString("status")));
            complaint.setResolution(resultSet.getString("resolution"));

            if (resultSet.getDate("resolved_date") != null) {
                complaint.setResolvedDate(resultSet.getDate("resolved_date").toLocalDate());
            }

            complaints.add(complaint);
        }

        return complaints.stream()
                .sorted(Comparator.comparing(Complaint::getComplaintDate))
                .collect(Collectors.toList());
    }

    public List<Complaint> viewComplaintsByUser(String userId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewComplaintsByUser(connection, userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
