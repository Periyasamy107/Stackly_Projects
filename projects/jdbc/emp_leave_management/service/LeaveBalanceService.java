package service;

import dao.EmployeeDAO;
import dao.LeaveBalanceDAO;
import dao.LeaveRequestDAO;
import enums.LeaveStatus;
import enums.LeaveType;
import exception.InsufficientLeaveBalanceException;
import model.LeaveBalance;
import model.LeaveRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LeaveBalanceService {

    private LeaveBalanceDAO leaveBalanceDAO;

    public LeaveBalanceService() {
        leaveBalanceDAO = new LeaveBalanceDAO();
    }



    public boolean createInitialBalances(String employeeId) {
        boolean casualCreated = leaveBalanceDAO.createInitialBalance(employeeId, LeaveType.CASUAL, 12);
        boolean sickCreated = leaveBalanceDAO.createInitialBalance(employeeId, LeaveType.SICK, 10);
        boolean earnedCreated = leaveBalanceDAO.createInitialBalance(employeeId, LeaveType.EARNED, 15);
        return casualCreated && sickCreated && earnedCreated;
    }


    public List<LeaveBalance> getEmployeeBalances(String employeeId) {
        return leaveBalanceDAO.getEmployeeBalances(employeeId);
    }


    public int getAvailableDays(Connection connection, String employeeId, String leaveType) {
        return leaveBalanceDAO.getAvailableDays(connection, employeeId, leaveType);
    }

    public boolean approveLeave(Connection connection, int requestId, String managerId)
            throws InsufficientLeaveBalanceException {

        try {

            connection.setAutoCommit(false);

            LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();
            LeaveRequest leaveRequest = leaveRequestDAO.getLeaveRequestById(connection, requestId);
            EmployeeDAO employeeDAO = new EmployeeDAO();
            LeaveBalanceDAO leaveBalanceDAO = new LeaveBalanceDAO();

            if (leaveRequest == null) {
                System.out.println("Leave request not found.");
                connection.rollback();
                return false;
            }

            if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
                System.out.println("Leave request is not pending.");
                connection.rollback();
                return false;
            }

            if (!employeeDAO.activeEmployeeExists(connection, leaveRequest.getEmployeeId())) {
                System.out.println("Employee is not active.");
                connection.rollback();
                return false;
            }

            String leaveType = leaveRequest.getLeaveType().name();

            int availableDays = leaveBalanceDAO.getAvailableDays(connection, leaveRequest.getEmployeeId(), leaveType);

            if (availableDays < 0) {
                System.out.println("Leave balance not found.");
                connection.rollback();
                return false;
            }

            if (availableDays < leaveRequest.getNumberOfDays()) {
                connection.rollback();
                throw new InsufficientLeaveBalanceException("Insufficient leave balance.");
            }

            boolean balanceUpdated = leaveBalanceDAO.updateUsedDays(
                            connection,
                            leaveRequest.getEmployeeId(),
                            leaveType,
                            leaveRequest.getNumberOfDays()
                    );

            if (!balanceUpdated) {
                System.out.println("Unable to update leave balance.");
                connection.rollback();
                return false;
            }

            boolean leaveApproved = leaveRequestDAO.approveLeave(connection, requestId, managerId);

            if (!leaveApproved) {
                System.out.println("Unable to approve leave request.");
                connection.rollback();
                return false;
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            System.out.println("Transaction failed.");
            System.out.println(e.getMessage());
            return false;
        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }




    public boolean rejectLeave(Connection connection, int requestId, String managerId) {

        try {
            connection.setAutoCommit(false);

            LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();
            LeaveRequest leaveRequest = leaveRequestDAO.getLeaveRequestById(connection, requestId);

            if (leaveRequest == null) {
                System.out.println("Leave request not found.");
                connection.rollback();
                return false;
            }

            if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
                System.out.println("Leave request is not pending.");
                connection.rollback();
                return false;
            }

            boolean rejected = leaveRequestDAO.rejectLeave(connection, requestId, managerId);

            if (!rejected) {
                connection.rollback();
                return false;
            }

            connection.commit();

            return true;
        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            System.out.println("Transaction failed.");
            System.out.println(e.getMessage());
            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }





    public boolean cancelLeave(Connection connection, int requestId) {

        try {

            connection.setAutoCommit(false);

            LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();
            LeaveRequest leaveRequest = leaveRequestDAO.getLeaveRequestById(connection, requestId);

            if (leaveRequest == null) {
                System.out.println("Leave request not found.");
                connection.rollback();
                return false;
            }

            LeaveStatus status = leaveRequest.getStatus();

            if (status == LeaveStatus.REJECTED) {
                System.out.println("Rejected leave cannot be cancelled.");
                connection.rollback();
                return false;
            }

            if (status == LeaveStatus.CANCELLED) {
                System.out.println("Leave is already cancelled.");
                connection.rollback();
                return false;
            }

            if (status == LeaveStatus.APPROVED) {
                boolean balanceRestored =
                        leaveBalanceDAO.restoreUsedDays(
                                connection,
                                leaveRequest.getEmployeeId(),
                                leaveRequest.getLeaveType().name(),
                                leaveRequest.getNumberOfDays()
                        );

                if (!balanceRestored) {
                    System.out.println("Unable to restore leave balance.");
                    connection.rollback();
                    return false;
                }
            }

            boolean cancelled = leaveRequestDAO.cancelLeave(connection, requestId);

            if (!cancelled) {
                System.out.println("Unable to cancel leave request.");
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();

                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.out.println("Leave cancellation transaction failed.");
            e.printStackTrace();
            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public int getTotalAllocatedDays(String employeeId) {

        List<LeaveBalance> balances = leaveBalanceDAO.getEmployeeBalances(employeeId);
        int total = 0;
        for (LeaveBalance balance : balances) {
            total += balance.getAllocatedDays();
        }
        return total;
    }


    public int getTotalUsedDays(String employeeId) {

        List<LeaveBalance> balances = leaveBalanceDAO.getEmployeeBalances(employeeId);
        int total = 0;
        for (LeaveBalance balance : balances) {
            total += balance.getUsedDays();
        }
        return total;
    }


    public int getTotalAvailableDays(String employeeId) {

        List<LeaveBalance> balances = leaveBalanceDAO.getEmployeeBalances(employeeId);
        int total = 0;
        for (LeaveBalance balance : balances) {
            total += balance.getAvailableDays();
        }
        return total;
    }

}
