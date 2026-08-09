package dao;

import enums.LeaveType;
import model.LeaveBalance;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveBalanceDAO {

    public int getAvailableDays(Connection connection, String employeeId, String leaveType) {

        String sql = """
                SELECT allocated_days, used_days
                FROM leave_balance
                WHERE employee_id = ?
                AND leave_type = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);
            preparedStatement.setString(2, String.valueOf(leaveType));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    int allocatedDays = resultSet.getInt("allocated_days");
                    int usedDays = resultSet.getInt("used_days");

                    return allocatedDays - usedDays;
                }
            }
        } catch (SQLException e) {
            System.out.println("\nError checking leave balance.\n");
            System.out.println(e.getMessage());
        }
        return -1;
    }


    public boolean updateUsedDays(Connection connection, String employeeId, String leaveType, int numberOfDays) {

        String sql = """
                UPDATE leave_balance
                SET used_days = used_days + ?
                WHERE employee_id = ?
                AND leave_type = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, numberOfDays);
            preparedStatement.setString(2, employeeId);
            preparedStatement.setString(3, leaveType);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nError updating leave balance.\n");
            System.out.println(e.getMessage());
        }
        return false;
    }



    public boolean createInitialBalance(String employeeId, LeaveType leaveType, int allocatedDays) {

        String sql = """
                INSERT INTO leave_balance
                (
                    employee_id,
                    leave_type,
                    allocated_days,
                    used_days
                )
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);
            preparedStatement.setString(2, leaveType.name());
            preparedStatement.setInt(3, allocatedDays);
            preparedStatement.setInt(4, 0);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error creating leave balance.");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public List<LeaveBalance> getEmployeeBalances(String employeeId) {

        List<LeaveBalance> balances = new ArrayList<>();

        String sql = """
                SELECT balance_id,
                       employee_id,
                       leave_type,
                       allocated_days,
                       used_days
                FROM leave_balance
                WHERE employee_id = ?
                ORDER BY leave_type
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    LeaveBalance balance = new LeaveBalance();

                    balance.setBalanceId(resultSet.getInt("balance_id"));
                    balance.setEmployeeId(resultSet.getString("employee_id"));
                    balance.setLeaveType(LeaveType.valueOf(resultSet.getString("leave_type")));
                    balance.setAllocatedDays(resultSet.getInt("allocated_days"));
                    balance.setUsedDays(resultSet.getInt("used_days"));

                    balances.add(balance);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving leave balance.");
            System.out.println(e.getMessage());
        }
        return balances;
    }




    public boolean restoreUsedDays(Connection connection, String employeeId, String leaveType, int numberOfDays) {

        String sql = """
            UPDATE leave_balance
            SET used_days = used_days - ?
            WHERE employee_id = ?
            AND leave_type = ?
            AND used_days >= ?
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, numberOfDays);
            preparedStatement.setString(2, employeeId);
            preparedStatement.setString(3, leaveType);
            preparedStatement.setInt(4, numberOfDays);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error restoring leave balance.");
            System.out.println(e.getMessage());
        }
        return false;
    }


}