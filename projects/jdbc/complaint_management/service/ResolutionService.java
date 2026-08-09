package service;

import dao.ResolutionDAO;
import database.DBConnectionHotel;
import enums.ComplaintCategory;
import enums.ComplaintStatus;
import model.Complaint;

import java.sql.*;

public class ResolutionService implements ResolutionDAO {
    @Override
    public boolean addResolution(Connection connection, String complaintId, String resolution) throws SQLException {
        if (resolution == null || resolution.trim().length() < 5) {
            throw new SQLException("Resolution must contain at least 5 characters.");
        }

        String query = """
                UPDATE complaints
                SET resolution = ?
                WHERE complaint_id = ?
                """;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, resolution);
            preparedStatement.setString(2, complaintId);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean addResolution(String complaintId,
                                 String resolution) {

        try (Connection connection = DBConnectionHotel.getDatabaseConnection()) {

            return addResolution(connection, complaintId, resolution);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public String viewResolution(Connection connection, String complaintId) throws SQLException {
        String query = """
                SELECT resolution
                FROM complaints
                WHERE complaint_id = ?
                """;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, complaintId);

            try (ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString("resolution");
                }
            }
        }

        return null;
    }

    public String viewResolution(String complaintId) {

        try (Connection connection = DBConnectionHotel.getDatabaseConnection()) {

            return viewResolution(connection, complaintId);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean markAsResolved(Connection connection, String complaintId, String resolution) throws SQLException {
        if (resolution == null || resolution.trim().length() < 5) {
            throw new SQLException("Resolution must contain at least 5 characters.");
        }

        String query = """
                UPDATE complaints
                SET status = ?,
                    resolution = ?,
                    resolved_date = ?
                WHERE complaint_id = ?
                """;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(
                    1,
                    ComplaintStatus.RESOLVED.name()
            );

            preparedStatement.setString(2, resolution);

            preparedStatement.setDate(
                    3,
                    Date.valueOf(java.time.LocalDate.now())
            );

            preparedStatement.setString(4, complaintId);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean markAsResolved(String complaintId,
                                  String resolution) {

        try (Connection connection = DBConnectionHotel.getDatabaseConnection()) {

            return markAsResolved(
                    connection,
                    complaintId,
                    resolution
            );

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }



    @Override
    public Complaint viewResolvedComplaint(Connection connection, String complaintId) throws SQLException {
        String query = """
                SELECT *
                FROM complaints
                WHERE complaint_id = ?
                AND status = ?
                """;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, complaintId);
            preparedStatement.setString(
                    2,
                    ComplaintStatus.RESOLVED.name()
            );
            try (ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    Complaint complaint = new Complaint();

                    complaint.setComplaintId(
                            resultSet.getString("complaint_id")
                    );

                    complaint.setUserId(
                            resultSet.getString("user_id")
                    );

                    complaint.setOfficerId(
                            resultSet.getString("officer_id")
                    );

                    complaint.setCategory(
                            ComplaintCategory.valueOf(
                                    resultSet.getString("category")
                            )
                    );

                    complaint.setDescription(
                            resultSet.getString("description")
                    );

                    complaint.setComplaintDate(
                            resultSet.getDate("complaint_date")
                                    .toLocalDate()
                    );

                    complaint.setStatus(
                            ComplaintStatus.valueOf(
                                    resultSet.getString("status")
                            )
                    );

                    complaint.setResolution(
                            resultSet.getString("resolution")
                    );

                    if (resultSet.getDate("resolved_date") != null) {

                        complaint.setResolvedDate(
                                resultSet.getDate("resolved_date")
                                        .toLocalDate()
                        );
                    }

                    return complaint;
                }
            }
        }
        return null;
    }


    public Complaint viewResolvedComplaint(String complaintId) {

        try (Connection connection = DBConnectionHotel.getDatabaseConnection()) {

            return viewResolvedComplaint(connection, complaintId);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
