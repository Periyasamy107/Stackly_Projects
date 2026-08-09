package dao;

import model.LeaveReport;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveReportDAO {

    public static List<LeaveReport> getAllLeaveReports() {

        List<LeaveReport> reports = new ArrayList<>();

        String sql = """
                SELECT
                    e.employee_id,
                    e.employee_name,
                    lr.request_id,
                    lr.leave_type,
                    lr.start_date,
                    lr.end_date,
                    lr.number_of_days,
                    lr.status
                FROM employees e
                JOIN leave_requests lr
                    ON e.employee_id = lr.employee_id
                ORDER BY lr.request_id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

                LeaveReport report = new LeaveReport(
                        resultSet.getString("employee_id"),
                        resultSet.getString("employee_name"),
                        resultSet.getInt("request_id"),
                        resultSet.getString("leave_type"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("end_date").toLocalDate(),
                        resultSet.getInt("number_of_days"),
                        resultSet.getString("status")
                );

                reports.add(report);
            }
        } catch (SQLException e) {
            System.out.println("Error generating leave report.");
            System.out.println(e.getMessage());
        }
        return reports;
    }



    public static List<LeaveReport> getLeaveReportsByEmployee(String employeeId) {

        List<LeaveReport> reports = new ArrayList<>();

        String sql = """
            SELECT
                e.employee_id,
                e.employee_name,
                lr.request_id,
                lr.leave_type,
                lr.start_date,
                lr.end_date,
                lr.number_of_days,
                lr.status
            FROM employees e
            JOIN leave_requests lr
                ON e.employee_id = lr.employee_id
            WHERE e.employee_id = ?
            ORDER BY lr.start_date DESC
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reports.add(
                            new LeaveReport(
                                    resultSet.getString("employee_id"),
                                    resultSet.getString("employee_name"),
                                    resultSet.getInt("request_id"),
                                    resultSet.getString("leave_type"),
                                    resultSet.getDate("start_date").toLocalDate(),
                                    resultSet.getDate("end_date").toLocalDate(),
                                    resultSet.getInt("number_of_days"),
                                    resultSet.getString("status")
                            )
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error generating employee report.");
            System.out.println(e.getMessage());
        }
        return reports;
    }
}
