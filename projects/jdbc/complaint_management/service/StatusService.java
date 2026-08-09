package service;

import dao.StatusDAO;
import database.DBConnection;
import enums.ComplaintCategory;
import enums.ComplaintStatus;
import exception.InvalidStatusException;
import exception.ComplaintNotFoundException;
import model.Complaint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatusService implements StatusDAO {

//    @Override
//    public boolean updateStatus(Connection connection,
//                                String complaintId,
//                                ComplaintStatus status) throws SQLException {
//
//        String query = """
//                UPDATE complaints
//                SET status = ?
//                WHERE complaint_id = ?
//                """;
//
//        PreparedStatement preparedStatement =
//                connection.prepareStatement(query);
//
//        preparedStatement.setString(1, status.name());
//        preparedStatement.setString(2, complaintId);
//
//        return preparedStatement.executeUpdate() > 0;
//    }

//    public boolean updateStatus(String complaintId,
//                                ComplaintStatus status) {
//
//        try (Connection connection = DBConnection.getDatabaseConnection()) {
//
//            return updateStatus(connection, complaintId, status);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

    public boolean updateStatus(Connection connection,
                                String complaintId,
                                ComplaintStatus status)
            throws SQLException, ComplaintNotFoundException,
            InvalidStatusException {

        if (status == null) {
            throw new InvalidStatusException("Invalid complaint status.");
        }

        String checkQuery = """
                SELECT status
                FROM complaints
                WHERE complaint_id = ?
                """;

        String updateQuery = """
                UPDATE complaints
                SET status = ?
                WHERE complaint_id = ?
                """;

        try (PreparedStatement checkStatement =
                     connection.prepareStatement(checkQuery)) {

            checkStatement.setString(1, complaintId);

            try (ResultSet resultSet =
                         checkStatement.executeQuery()) {

                if (!resultSet.next()) {
                    throw new ComplaintNotFoundException(
                            "Complaint not found: " + complaintId);
                }

                ComplaintStatus currentStatus =
                        ComplaintStatus.valueOf(
                                resultSet.getString("status"));

                validateStatusTransition(currentStatus, status);
            }
        }

        try (PreparedStatement updateStatement =
                     connection.prepareStatement(updateQuery)) {

            updateStatement.setString(1, status.name());
            updateStatement.setString(2, complaintId);

            return updateStatement.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(String complaintId,
                                ComplaintStatus status) {

        try (Connection connection =
                     DBConnection.getDatabaseConnection()) {

            return updateStatus(
                    connection,
                    complaintId,
                    status
            );

        } catch (SQLException |
                 ComplaintNotFoundException |
                 InvalidStatusException e) {

            System.out.println(e.getMessage());
        }

        return false;
    }

    private void validateStatusTransition(
            ComplaintStatus currentStatus,
            ComplaintStatus newStatus)
            throws InvalidStatusException {

        if (currentStatus == newStatus) {
            throw new InvalidStatusException(
                    "Complaint is already in " + newStatus + " status."
            );
        }

        switch (currentStatus) {

            case REGISTERED:

                if (newStatus != ComplaintStatus.ASSIGNED &&
                        newStatus != ComplaintStatus.ON_HOLD) {

                    throw new InvalidStatusException(
                            "REGISTERED complaint can only be ASSIGNED or ON_HOLD."
                    );
                }
                break;

            case ASSIGNED:

                if (newStatus != ComplaintStatus.IN_PROGRESS &&
                        newStatus != ComplaintStatus.ON_HOLD) {

                    throw new InvalidStatusException(
                            "ASSIGNED complaint can only be IN_PROGRESS or ON_HOLD."
                    );
                }
                break;

            case IN_PROGRESS:

                if (newStatus != ComplaintStatus.RESOLVED &&
                        newStatus != ComplaintStatus.ON_HOLD) {

                    throw new InvalidStatusException(
                            "IN_PROGRESS complaint can only be RESOLVED or ON_HOLD."
                    );
                }
                break;

            case ON_HOLD:

                if (newStatus != ComplaintStatus.ASSIGNED &&
                        newStatus != ComplaintStatus.IN_PROGRESS) {

                    throw new InvalidStatusException(
                            "ON_HOLD complaint can only return to ASSIGNED or IN_PROGRESS."
                    );
                }
                break;

            case RESOLVED:

                if (newStatus != ComplaintStatus.CLOSED) {

                    throw new InvalidStatusException(
                            "RESOLVED complaint can only be CLOSED."
                    );
                }
                break;

            case CLOSED:

                throw new InvalidStatusException(
                        "CLOSED complaint cannot be changed."
                );
        }
    }

    @Override
    public ComplaintStatus viewStatus(Connection connection,
                                      String complaintId) throws SQLException {

        String query = """
                SELECT status
                FROM complaints
                WHERE complaint_id = ?
                """;

        PreparedStatement preparedStatement =
                connection.prepareStatement(query);

        preparedStatement.setString(1, complaintId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {

            return ComplaintStatus.valueOf(
                    resultSet.getString("status"));
        }

        return null;
    }

    public ComplaintStatus viewStatus(String complaintId) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewStatus(connection, complaintId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Complaint> viewComplaintsByStatus(
            Connection connection,
            ComplaintStatus status) throws SQLException {

        String query = """
                SELECT *
                FROM complaints
                WHERE status = ?
                """;

        List<Complaint> complaints = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, status.name());

            try (ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    complaints.add(mapComplaint(resultSet));
                }
            }
        }
        return complaints.stream()
                .sorted(Comparator.comparing(
                        Complaint::getComplaintDate))
                .collect(Collectors.toList());
    }

    public List<Complaint> viewComplaintsByStatus(
            ComplaintStatus status) {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewComplaintsByStatus(connection, status);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    @Override
    public List<Complaint> viewPendingComplaints(
            Connection connection) throws SQLException {

        return viewComplaintsByStatus(
                connection,
                ComplaintStatus.REGISTERED);
    }

    public List<Complaint> viewPendingComplaints() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewPendingComplaints(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Complaint> viewInProgressComplaints(
            Connection connection) throws SQLException {

        return viewComplaintsByStatus(
                connection,
                ComplaintStatus.IN_PROGRESS);
    }

    public List<Complaint> viewInProgressComplaints() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewInProgressComplaints(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Complaint> viewResolvedComplaints(
            Connection connection) throws SQLException {

        return viewComplaintsByStatus(
                connection,
                ComplaintStatus.RESOLVED);
    }

    public List<Complaint> viewResolvedComplaints() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewResolvedComplaints(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Complaint> viewClosedComplaints(
            Connection connection) throws SQLException {

        return viewComplaintsByStatus(
                connection,
                ComplaintStatus.CLOSED);
    }

    public List<Complaint> viewClosedComplaints() {

        try (Connection connection = DBConnection.getDatabaseConnection()) {

            return viewClosedComplaints(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    private Complaint mapComplaint(ResultSet resultSet)
            throws SQLException {

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

        return complaint;
    }
}
