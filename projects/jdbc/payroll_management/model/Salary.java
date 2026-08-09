package model;

import java.time.LocalDate;

public class Salary {

    private int salaryId;
    private String employeeId;
    private double basicSalary;
    private double hra;
    private double allowance;
    private double grossSalary;
    private LocalDate effectiveDate;

    public Salary() {
    }

    public Salary(int salaryId, String employeeId, double basicSalary,
                  double hra, double allowance, double grossSalary,
                  LocalDate effectiveDate) {

        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.allowance = allowance;
        this.grossSalary = grossSalary;
        this.effectiveDate = effectiveDate;
    }

    public Salary(String employeeId, double basicSalary,
                  double hra, double allowance, double grossSalary,
                  LocalDate effectiveDate) {

        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.allowance = allowance;
        this.grossSalary = grossSalary;
        this.effectiveDate = effectiveDate;
    }

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "salaryId=" + salaryId +
                ", employeeId='" + employeeId + '\'' +
                ", basicSalary=" + basicSalary +
                ", hra=" + hra +
                ", allowance=" + allowance +
                ", grossSalary=" + grossSalary +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
