package service;

import dao.AssignmentDAO;
import database.DBConnection;
import enums.ComplaintCategory;
import enums.ComplaintStatus;
import model.Complaint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AssignmentService implements AssignmentDAO {
    @Override
    public boolean assignOfficer(Connection connection, String complaintId, String officerId) throws SQLException {
        String query = """
                UPDATE complaints
                SET officer_id = ?,
                    status = ?
                WHERE complaint_id = ?
                """;

        PreparedStatement preparedStatement =
                connection.prepareStatement(query);

        preparedStatement.setString(1, officerId);
        preparedStatement.setString(2, ComplaintStatus.ASSIGNED.name());
        preparedStatement.setString(3, complaintId);

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean assignOfficer(String complaintId,
                                 String officerId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return assignOfficer(connection,
                    complaintId,
                    officerId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean reassignOfficer(Connection connection, String complaintId, String officerId) throws SQLException {
        String query = """
                UPDATE complaints
                SET officer_id = ?
                WHERE complaint_id = ?
                """;

        PreparedStatement preparedStatement =
                connection.prepareStatement(query);

        preparedStatement.setString(1, officerId);
        preparedStatement.setString(2, complaintId);

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean reassignOfficer(String complaintId,
                                   String officerId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return reassignOfficer(connection,
                    complaintId,
                    officerId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public List<Complaint> viewAssignedComplaints(Connection connection)
            throws SQLException {

        String query = """
            SELECT *
            FROM complaints
            WHERE officer_id IS NOT NULL
            ORDER BY complaint_date
            """;

        List<Complaint> complaints = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Complaint complaint = new Complaint();

                complaint.setComplaintId(
                        resultSet.getString("complaint_id"));

                complaint.setUserId(
                        resultSet.getString("user_id"));

                complaint.setOfficerId(
                        resultSet.getString("officer_id"));

                complaint.setCategory(
                        ComplaintCategory.valueOf(
                                resultSet.getString("category")));

                complaint.setDescription(
                        resultSet.getString("description"));

                complaint.setComplaintDate(
                        resultSet.getDate("complaint_date")
                                .toLocalDate());

                complaint.setStatus(
                        ComplaintStatus.valueOf(
                                resultSet.getString("status")));

                complaint.setResolution(
                        resultSet.getString("resolution"));

                if (resultSet.getDate("resolved_date") != null) {

                    complaint.setResolvedDate(
                            resultSet.getDate("resolved_date")
                                    .toLocalDate());
                }

                complaints.add(complaint);
            }
        }

        return complaints.stream()
                .sorted(Comparator.comparing(
                        Complaint::getComplaintDate))
                .collect(Collectors.toList());
    }

    public List<Complaint> viewAssignedComplaints() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewAssignedComplaints(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Complaint> viewUnassignedComplaints(Connection connection)
            throws SQLException {

        String query = """
            SELECT *
            FROM complaints
            WHERE officer_id IS NULL
            ORDER BY complaint_date
            """;

        List<Complaint> complaints = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Complaint complaint = new Complaint();

                complaint.setComplaintId(
                        resultSet.getString("complaint_id"));

                complaint.setUserId(
                        resultSet.getString("user_id"));

                complaint.setOfficerId(
                        resultSet.getString("officer_id"));

                complaint.setCategory(
                        ComplaintCategory.valueOf(
                                resultSet.getString("category")));

                complaint.setDescription(
                        resultSet.getString("description"));

                complaint.setComplaintDate(
                        resultSet.getDate("complaint_date")
                                .toLocalDate());

                complaint.setStatus(
                        ComplaintStatus.valueOf(
                                resultSet.getString("status")));

                complaint.setResolution(
                        resultSet.getString("resolution"));

                if (resultSet.getDate("resolved_date") != null) {

                    complaint.setResolvedDate(
                            resultSet.getDate("resolved_date")
                                    .toLocalDate());
                }

                complaints.add(complaint);
            }
        }

        return complaints.stream()
                .sorted(Comparator.comparing(
                        Complaint::getComplaintDate))
                .collect(Collectors.toList());
    }

    public List<Complaint> viewUnassignedComplaints() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewUnassignedComplaints(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
