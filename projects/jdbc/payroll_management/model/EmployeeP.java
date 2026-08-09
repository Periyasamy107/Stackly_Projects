package model;

import java.time.LocalDate;

public class EmployeeP {

    private String employeeId;
    private String employeeName;
    private String department;
    private String designation;
    private String email;
    private String phone;
    private double basicSalary;
    private double hra;
    private double allowance;
    private String status;
    private LocalDate joiningDate;

    public EmployeeP() {
    }

    public EmployeeP(String employeeId, String employeeName, String department,
                     String designation, String email, String phone,
                     double basicSalary, double hra, double allowance,
                     String status, LocalDate joiningDate) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.allowance = allowance;
        this.status = status;
        this.joiningDate = joiningDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getHra() {
        return hra;
    }

    public void setHra(double hra) {
        this.hra = hra;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    @Override
    public String toString() {
        return "EmployeeP{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", department='" + department + '\'' +
                ", designation='" + designation + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", basicSalary=" + basicSalary +
                ", hra=" + hra +
                ", allowance=" + allowance +
                ", status='" + status + '\'' +
                ", joiningDate=" + joiningDate +
                '}';
    }
}
