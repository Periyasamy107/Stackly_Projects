package model;

import java.time.LocalDate;

public class LeaveReport {

    private String employeeId;
    private String employeeName;
    private int requestId;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfDays;
    private String status;

    public LeaveReport(
            String employeeId,
            String employeeName,
            int requestId,
            String leaveType,
            LocalDate startDate,
            LocalDate endDate,
            int numberOfDays,
            String status) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.requestId = requestId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfDays = numberOfDays;
        this.status = status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {

        return "EmployeeP ID   : " + employeeId +
                "\nEmployeeP Name : " + employeeName +
                "\nRequest ID    : " + requestId +
                "\nLeave Type    : " + leaveType +
                "\nStart Date    : " + startDate +
                "\nEnd Date      : " + endDate +
                "\nNumber of Days: " + numberOfDays +
                "\nStatus        : " + status;
    }
}