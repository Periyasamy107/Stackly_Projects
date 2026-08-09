package dao;

import enums.LeaveStatus;
import enums.LeaveType;
import model.LeaveRequest;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDAO {

    public boolean applyLeave(LeaveRequest leaveRequest) {

        String sql = """
                INSERT INTO leave_requests
                (
                    employee_id,
                    leave_type,
                    start_date,
                    end_date,
                    number_of_days,
                    reason,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, leaveRequest.getEmployeeId());
            preparedStatement.setString(2, leaveRequest.getLeaveType().name());
            preparedStatement.setDate(3, java.sql.Date.valueOf(leaveRequest.getStartDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(leaveRequest.getEndDate()));
            preparedStatement.setInt(5, leaveRequest.getNumberOfDays());
            preparedStatement.setString(6, leaveRequest.getReason());
            preparedStatement.setString(7, leaveRequest.getStatus().name());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("\nError while applying leave.\n");
            System.out.println(e.getMessage());
        }
        return false;
    }



    public List<LeaveRequest> getPendingLeaveRequests() {

        List<LeaveRequest> leaveRequests = new ArrayList<>();

        String sql = """
            SELECT request_id,
                   employee_id,
                   leave_type,
                   start_date,
                   end_date,
                   number_of_days,
                   reason,
                   status,
                   applied_date,
                   approved_date,
                   approved_by
            FROM leave_requests
            WHERE status = 'PENDING'
            ORDER BY applied_date
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                LeaveRequest leaveRequest = new LeaveRequest();

                leaveRequest.setRequestId(resultSet.getInt("request_id"));
                leaveRequest.setEmployeeId(resultSet.getString("employee_id"));
                leaveRequest.setLeaveType(LeaveType.valueOf(resultSet.getString("leave_type")));
                leaveRequest.setStartDate(resultSet.getDate("start_date").toLocalDate());
                leaveRequest.setEndDate(resultSet.getDate("end_date").toLocalDate());
                leaveRequest.setNumberOfDays(resultSet.getInt("number_of_days"));
                leaveRequest.setReason(resultSet.getString("reason"));
                leaveRequest.setStatus(LeaveStatus.valueOf(resultSet.getString("status")));
                leaveRequest.setAppliedDate(resultSet.getDate("applied_date").toLocalDate());

                if (resultSet.getDate("approved_date") != null) {
                    leaveRequest.setApprovedDate(resultSet.getDate("approved_date").toLocalDate());
                }

                leaveRequest.setApprovedBy(resultSet.getString("approved_by"));

                leaveRequests.add(leaveRequest);
            }

        } catch (SQLException e) {
            System.out.println("\nError retrieving pending leave requests.\n");
            System.out.println(e.getMessage());
        }
        return leaveRequests;
    }


    public LeaveRequest getLeaveRequestById(Connection connection, int requestId) {

        String sql = """
            SELECT request_id,
                   employee_id,
                   leave_type,
                   start_date,
                   end_date,
                   number_of_days,
                   reason,
                   status,
                   applied_date,
                   approved_date,
                   approved_by
            FROM leave_requests
            WHERE request_id = ?
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, requestId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    LeaveRequest leaveRequest = new LeaveRequest();

                    leaveRequest.setRequestId(resultSet.getInt("request_id"));
                    leaveRequest.setEmployeeId(resultSet.getString("employee_id"));
                    leaveRequest.setLeaveType(LeaveType.valueOf(resultSet.getString("leave_type")));
                    leaveRequest.setStartDate(resultSet.getDate("start_date").toLocalDate());
                    leaveRequest.setEndDate(resultSet.getDate("end_date").toLocalDate());
                    leaveRequest.setNumberOfDays(resultSet.getInt("number_of_days"));
                    leaveRequest.setReason(resultSet.getString("reason"));
                    leaveRequest.setStatus(LeaveStatus.valueOf(resultSet.getString("status")));

                    Date appliedDate = resultSet.getDate("applied_date");
                    if(appliedDate != null) {
                        leaveRequest.setAppliedDate(appliedDate.toLocalDate());
                    }

                    Date approvedDate = resultSet.getDate("approved_date");
                    if (approvedDate != null) {
                        leaveRequest.setApprovedDate(approvedDate.toLocalDate());
                    }

                    leaveRequest.setApprovedBy(resultSet.getString("approved_by"));

                    return leaveRequest;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving leave request.");
            System.out.println(e.getMessage());
        }
        return null;
    }



    public boolean approveLeave(Connection connection, int requestId, String managerId) {

        String sql = """
            UPDATE leave_requests
            SET status = 'APPROVED',
                approved_date = CURRENT_DATE,
                approved_by = ?
            WHERE request_id = ?
            AND status = 'PENDING'
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, managerId);
            preparedStatement.setInt(2, requestId);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error approving leave request.");
            System.out.println(e.getMessage());
        }
        return false;
    }



    public boolean rejectLeave(Connection connection, int requestId, String managerId) {

        String sql = """
            UPDATE leave_requests
            SET status = 'REJECTED',
                approved_date = CURRENT_DATE,
                approved_by = ?
            WHERE request_id = ?
            AND status = 'PENDING'
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, managerId);
            preparedStatement.setInt(2, requestId);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error rejecting leave request.");
            System.out.println(e.getMessage());
        }
        return false;
    }



    public boolean cancelLeave(Connection connection, int requestId) {

        String sql = """
            UPDATE leave_requests
            SET status = 'CANCELLED'
            WHERE request_id = ?
            AND status IN ('PENDING', 'APPROVED')
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, requestId);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error cancelling leave request.");
            System.out.println(e.getMessage());
        }
        return false;
    }



    public List<LeaveRequest> getLeavesByEmployeeId(Connection connection, String employeeId) {

        List<LeaveRequest> leaves = new ArrayList<>();

        String sql = """
            SELECT request_id,
                   employee_id,
                   leave_type,
                   start_date,
                   end_date,
                   number_of_days,
                   reason,
                   status,
                   approved_by
            FROM leave_requests
            WHERE employee_id = ?
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    LeaveRequest leaveRequest = new LeaveRequest();

                    leaveRequest.setRequestId(resultSet.getInt("request_id"));
                    leaveRequest.setEmployeeId(resultSet.getString("employee_id"));
                    leaveRequest.setLeaveType(LeaveType.valueOf(resultSet.getString("leave_type")));
                    leaveRequest.setStartDate(resultSet.getDate("start_date").toLocalDate());
                    leaveRequest.setEndDate(resultSet.getDate("end_date").toLocalDate());
                    leaveRequest.setNumberOfDays(resultSet.getInt("number_of_days"));
                    leaveRequest.setReason(resultSet.getString("reason"));
                    leaveRequest.setStatus(LeaveStatus.valueOf(resultSet.getString("status")));
                    leaveRequest.setApprovedBy(resultSet.getString("approved_by"));

                    leaves.add(leaveRequest);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving leave request.");
            System.out.println(e.getMessage());
        }
        return leaves;
    }
}
