package service;

import dao.ComplaintReportDAO;
import database.DBConnectionHotel;
import enums.ComplaintCategory;
import enums.ComplaintStatus;
import model.Complaint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ComplaintReportService implements ComplaintReportDAO {
    @Override
    public Map<String, Long> getComplaintCountByStatus(Connection connection) throws SQLException {
        String query = """
                SELECT status, COUNT(*) AS total
                FROM complaints
                GROUP BY status
                """;

        Map<String, Long> result = new HashMap<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query);
             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                result.put(
                        resultSet.getString("status"),
                        resultSet.getLong("total")
                );
            }
        }

        return result;
    }

    public Map<String, Long> getComplaintCountByStatus() {

        try (Connection connection = DBConnectionHotel.getDatabaseConnection()) {

            return getComplaintCountByStatus(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    @Override
    public Map<String, Long> getComplaintCountByCategory(Connection connection) throws SQLException {
        String query = """
                SELECT category, COUNT(*) AS total
                FROM complaints
                GROUP BY category
                """;

        Map<String, Long> result = new HashMap<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query);
             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                result.put(
                        resultSet.getString("category"),
                        resultSet.getLong("total")
                );
            }
        }

        return result;
    }

    public Map<String, Long> getComplaintCountByCategory() {

        try (Connection connection = DBConnectionHotel.getDatabaseConnection()) {

            return getComplaintCountByCategory(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    @Override
    public Map<String, Long> getComplaintCountByOfficer(Connection connection) throws SQLException {
        String query = """
                SELECT
                    COALESCE(officer_id, 'UNASSIGNED') AS officer,
                    COUNT(*) AS total
                FROM complaints
                GROUP BY officer_id
                """;

        Map<String, Long> result = new HashMap<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query);
             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                result.put(
                        resultSet.getString("officer"),
                        resultSet.getLong("total")
                );
            }
        }

        return result;
    }

    public Map<String, Long> getComplaintCountByOfficer() {

        try (Connection connection = DBConnectionHotel.getDatabaseConnection()) {

            return getComplaintCountByOfficer(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    @Override
    public List<Complaint> getUnresolvedComplaints(Connection connection) throws SQLException {
        String query = """
                SELECT *
                FROM complaints
                WHERE status <> ?
                """;

        return getComplaints(
                connection,
                query,
                ComplaintStatus.CLOSED.name()
        );
    }

    public List<Complaint> getUnresolvedComplaints() {

        try (Connection connection = DBConnectionHotel.getDatabaseConnection()) {

            return getUnresolvedComplaints(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Complaint> getResolvedComplaints(Connection connection) throws SQLException {
        String query = """
                SELECT *
                FROM complaints
                WHERE status = ?
                """;

        return getComplaints(
                connection,
                query,
                ComplaintStatus.RESOLVED.name()
        );
    }

    public List<Complaint> getResolvedComplaints() {

        try (Connection connection = DBConnectionHotel.getDatabaseConnection()) {

            return getResolvedComplaints(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private List<Complaint> getComplaints(
            Connection connection,
            String query,
            String status) throws SQLException {

        List<Complaint> complaints = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, status);

            try (ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                while (resultSet.next()) {

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

                    complaints.add(complaint);
                }
            }
        }
        return complaints.stream()
                .sorted(
                        Comparator.comparing(
                                Complaint::getComplaintDate
                        ).reversed()
                )
                .collect(Collectors.toList());
    }
}
