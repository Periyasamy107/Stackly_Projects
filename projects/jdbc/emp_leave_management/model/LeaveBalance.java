package model;

import enums.LeaveType;

public class LeaveBalance {

    private int balanceId;
    private String employeeId;
    private LeaveType leaveType;
    private int allocatedDays;
    private int usedDays;

    public LeaveBalance() {
    }

    public LeaveBalance(String employeeId,
                        LeaveType leaveType,
                        int allocatedDays,
                        int usedDays) {

        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.allocatedDays = allocatedDays;
        this.usedDays = usedDays;
    }

    public int getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public int getAllocatedDays() {
        return allocatedDays;
    }

    public void setAllocatedDays(int allocatedDays) {
        this.allocatedDays = allocatedDays;
    }

    public int getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(int usedDays) {
        this.usedDays = usedDays;
    }

    public int getAvailableDays() {
        return allocatedDays - usedDays;
    }

    @Override
    public String toString() {
        return "LeaveBalance{" +
                "balanceId=" + balanceId +
                ", employeeId='" + employeeId + '\'' +
                ", leaveType=" + leaveType +
                ", allocatedDays=" + allocatedDays +
                ", usedDays=" + usedDays +
                ", availableDays=" + getAvailableDays() +
                '}';
    }
}