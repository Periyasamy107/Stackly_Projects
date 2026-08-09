package service;

import dao.EmployeeDAO;
import dao.LeaveRequestDAO;
import enums.LeaveStatus;
import exception.InvalidLeaveRequestException;
import model.LeaveRequest;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LeaveRequestService {

    private LeaveRequestDAO leaveRequestDAO;
    private EmployeeDAO employeeDAO;

    public LeaveRequestService() {

        leaveRequestDAO = new LeaveRequestDAO();
        employeeDAO = new EmployeeDAO();
    }

    public boolean applyLeave(Connection connection, LeaveRequest leaveRequest)
            throws InvalidLeaveRequestException {

        if (!employeeDAO.activeEmployeeExists(connection, leaveRequest.getEmployeeId())) {
            System.out.println("Employee does not exist.");
            return false;
        }

        if (leaveRequest.getStartDate().isAfter(leaveRequest.getEndDate())) {
            throw new InvalidLeaveRequestException("Start date cannot be after end date.");
        }

        if (leaveRequest.getStartDate().isBefore(LocalDate.now())) {
            throw new InvalidLeaveRequestException("Leave cannot start in the past.");
        }

        if (leaveRequest.getReason() == null || leaveRequest.getReason().trim().isEmpty()) {
            System.out.println("Reason cannot be empty.");
            return false;
        }

        long days = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;

        leaveRequest.setNumberOfDays((int) days);

        if(leaveRequest.getNumberOfDays() <= 0) {
            throw new InvalidLeaveRequestException("Number of leave days must be positive.");
        }

        if (days <= 0) {
            throw new InvalidLeaveRequestException("Invalid leave duration.");
        }

        leaveRequest.setStatus(LeaveStatus.PENDING);
        return leaveRequestDAO.applyLeave(leaveRequest);
    }


    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestDAO.getPendingLeaveRequests();
    }


    public LeaveRequest getLeaveRequestById(Connection connection, int requestId) {
        return leaveRequestDAO.getLeaveRequestById(connection, requestId);
    }


    public List<LeaveRequest> getLeavesByEmployeeID(Connection connection, String employeeId) {
        return leaveRequestDAO.getLeavesByEmployeeId(connection, employeeId);
    }

}
