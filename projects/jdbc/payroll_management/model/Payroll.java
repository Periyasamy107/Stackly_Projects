package model;

import java.time.LocalDate;

public class Payroll {

    private int payrollId;
    private String employeeId;
    private LocalDate payMonth;
    private double basicSalary;
    private double hra;
    private double allowance;
    private double grossSalary;
    private double tax;
    private double otherDeduction;
    private double totalDeduction;
    private double netSalary;
    private String paymentStatus;
    private LocalDate generatedDate;

    public Payroll() {
    }

    public Payroll(int payrollId, String employeeId, LocalDate payMonth,
                   double basicSalary, double hra, double allowance,
                   double grossSalary, double tax, double otherDeduction,
                   double totalDeduction, double netSalary,
                   String paymentStatus, LocalDate generatedDate) {

        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.payMonth = payMonth;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.allowance = allowance;
        this.grossSalary = grossSalary;
        this.tax = tax;
        this.otherDeduction = otherDeduction;
        this.totalDeduction = totalDeduction;
        this.netSalary = netSalary;
        this.paymentStatus = paymentStatus;
        this.generatedDate = generatedDate;
    }

    public Payroll(String employeeId, LocalDate payMonth,
                   double basicSalary, double hra, double allowance,
                   double grossSalary, double tax, double otherDeduction,
                   double totalDeduction, double netSalary,
                   String paymentStatus, LocalDate generatedDate) {

        this.employeeId = employeeId;
        this.payMonth = payMonth;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.allowance = allowance;
        this.grossSalary = grossSalary;
        this.tax = tax;
        this.otherDeduction = otherDeduction;
        this.totalDeduction = totalDeduction;
        this.netSalary = netSalary;
        this.paymentStatus = paymentStatus;
        this.generatedDate = generatedDate;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(LocalDate payMonth) {
        this.payMonth = payMonth;
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

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getOtherDeduction() {
        return otherDeduction;
    }

    public void setOtherDeduction(double otherDeduction) {
        this.otherDeduction = otherDeduction;
    }

    public double getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(double totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "payrollId=" + payrollId +
                ", employeeId='" + employeeId + '\'' +
                ", payMonth=" + payMonth +
                ", basicSalary=" + basicSalary +
                ", hra=" + hra +
                ", allowance=" + allowance +
                ", grossSalary=" + grossSalary +
                ", tax=" + tax +
                ", otherDeduction=" + otherDeduction +
                ", totalDeduction=" + totalDeduction +
                ", netSalary=" + netSalary +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", generatedDate=" + generatedDate +
                '}';
    }
}
