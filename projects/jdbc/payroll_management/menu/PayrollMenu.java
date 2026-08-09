package menu;


import model.EmployeeP;
import model.Payroll;
import model.Salary;
import service.PayrollService;
import util.InputUtilPayroll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollMenu {

    private final PayrollService payrollService;

    public PayrollMenu() {
        payrollService = new PayrollService();
    }

    // ============================================================
    // MAIN MENU
    // ============================================================

    public void start() {

        while (true) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("             PAYROLL MANAGEMENT SYSTEM");
            System.out.println("==================================================");
            System.out.println("1. Employee Management");
            System.out.println("2. Salary Management");
            System.out.println("3. Payroll Processing");
            System.out.println("4. Generate Payslip");
            System.out.println("5. Payroll Reports");
            System.out.println("6. Exit");
            System.out.println("==================================================");

            int choice = InputUtilPayroll.readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    employeeMenu();
                    break;

                case 2:
                    salaryMenu();
                    break;

                case 3:
                    payrollMenu();
                    break;

                case 4:
                    generatePayslip();
                    break;

                case 5:
                    reportMenu();
                    break;

                case 6:
                    System.out.println("Exiting Payroll Management System...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    // ============================================================
    // EMPLOYEE MENU
    // ============================================================

    private void employeeMenu() {

        while (true) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("              EMPLOYEE MANAGEMENT");
            System.out.println("==================================================");
            System.out.println("1. Register Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Deactivate Employee");
            System.out.println("6. View Active Employees");
            System.out.println("7. Back");
            System.out.println("==================================================");

            int choice = InputUtilPayroll.readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    registerEmployee();
                    break;

                case 2:
                    viewAllEmployees();
                    break;

                case 3:
                    searchEmployee();
                    break;

                case 4:
                    updateEmployee();
                    break;

                case 5:
                    deactivateEmployee();
                    break;

                case 6:
                    viewActiveEmployees();
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    // ============================================================
    // REGISTER EMPLOYEE
    // ============================================================

    private void registerEmployee() {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("                REGISTER EMPLOYEE");
        System.out.println("==================================================");

        String employeeId =
                InputUtilPayroll.readString("Enter Employee ID: ");

        String employeeName =
                InputUtilPayroll.readString("Enter Employee Name: ");

        String department =
                InputUtilPayroll.readString("Enter Department: ");

        String designation =
                InputUtilPayroll.readString("Enter Designation: ");

        String email =
                InputUtilPayroll.readEmail("Enter Email: ");

        String phone =
                InputUtilPayroll.readPhone("Enter Phone: ");

        double basicSalary =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Basic Salary: "
                );

        double hra =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter HRA: "
                );

        double allowance =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Allowance: "
                );

        LocalDate joiningDate =
                InputUtilPayroll.readDate(
                        "Enter Joining Date (yyyy-MM-dd): "
                );

        EmployeeP employeeP = new EmployeeP(
                employeeId,
                employeeName,
                department,
                designation,
                email,
                phone,
                basicSalary,
                hra,
                allowance,
                "ACTIVE",
                joiningDate
        );

        boolean result =
                payrollService.registerEmployee(employeeP);

        if (result) {

            System.out.println(
                    "Employee registered successfully."
            );

        } else {

            System.out.println(
                    "Employee registration failed."
            );
        }
    }


    // ============================================================
    // VIEW ALL EMPLOYEES
    // ============================================================

    private void viewAllEmployees() {

        List<EmployeeP> employeePS =
                payrollService.getAllEmployees();

        if (employeePS.isEmpty()) {

            System.out.println("No employee found.");
            return;
        }

        System.out.println();
        System.out.println("==========================================================================");
        System.out.println("                         ALL EMPLOYEES");
        System.out.println("==========================================================================");

        for (EmployeeP employeeP : employeePS) {

            displayEmployee(employeeP);

            System.out.println(
                    "--------------------------------------------------------------------------"
            );
        }
    }


    // ============================================================
    // SEARCH EMPLOYEE
    // ============================================================

    private void searchEmployee() {

        String employeeId =
                InputUtilPayroll.readString(
                        "Enter Employee ID: "
                );

        EmployeeP employeeP =
                payrollService.getEmployeeById(employeeId);

        if (employeeP == null) {

            System.out.println("Employee not found.");
            return;
        }

        System.out.println();
        displayEmployee(employeeP);
    }


    // ============================================================
    // UPDATE EMPLOYEE
    // ============================================================

    private void updateEmployee() {

        String employeeId =
                InputUtilPayroll.readString(
                        "Enter Employee ID to update: "
                );

        EmployeeP existing =
                payrollService.getEmployeeById(employeeId);

        if (existing == null) {

            System.out.println("Employee not found.");
            return;
        }

        System.out.println();
        System.out.println("Enter new employee details.");

        String employeeName =
                InputUtilPayroll.readString(
                        "Enter Employee Name: "
                );

        String department =
                InputUtilPayroll.readString(
                        "Enter Department: "
                );

        String designation =
                InputUtilPayroll.readString(
                        "Enter Designation: "
                );

        String email =
                InputUtilPayroll.readEmail(
                        "Enter Email: "
                );

        String phone =
                InputUtilPayroll.readPhone(
                        "Enter Phone: "
                );

        double basicSalary =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Basic Salary: "
                );

        double hra =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter HRA: "
                );

        double allowance =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Allowance: "
                );

        String status =
                InputUtilPayroll.readStatus(
                        "Enter Status (ACTIVE/INACTIVE): "
                );

        LocalDate joiningDate =
                InputUtilPayroll.readDate(
                        "Enter Joining Date (yyyy-MM-dd): "
                );

        EmployeeP employeeP = new EmployeeP(
                employeeId,
                employeeName,
                department,
                designation,
                email,
                phone,
                basicSalary,
                hra,
                allowance,
                status,
                joiningDate
        );

        boolean result =
                payrollService.updateEmployee(employeeP);

        if (result) {

            System.out.println(
                    "Employee updated successfully."
            );

        } else {

            System.out.println(
                    "Employee update failed."
            );
        }
    }


    // ============================================================
    // DEACTIVATE EMPLOYEE
    // ============================================================

    private void deactivateEmployee() {

        String employeeId =
                InputUtilPayroll.readString(
                        "Enter Employee ID: "
                );

        EmployeeP employeeP =
                payrollService.getEmployeeById(employeeId);

        if (employeeP == null) {

            System.out.println("Employee not found.");
            return;
        }

        if (employeeP.getStatus().equalsIgnoreCase("INACTIVE")) {

            System.out.println(
                    "Employee is already inactive."
            );

            return;
        }

        boolean result =
                payrollService.deactivateEmployee(employeeId);

        if (result) {

            System.out.println(
                    "Employee deactivated successfully."
            );

        } else {

            System.out.println(
                    "Unable to deactivate employee."
            );
        }
    }


    // ============================================================
    // VIEW ACTIVE EMPLOYEES
    // ============================================================

    private void viewActiveEmployees() {

        List<EmployeeP> employeePS =
                payrollService.getActiveEmployees();

        if (employeePS.isEmpty()) {

            System.out.println(
                    "No active employee found."
            );

            return;
        }

        System.out.println();
        System.out.println("==================================================");
        System.out.println("                ACTIVE EMPLOYEES");
        System.out.println("==================================================");

        for (EmployeeP employeeP : employeePS) {

            displayEmployee(employeeP);

            System.out.println(
                    "--------------------------------------------------"
            );
        }
    }


    // ============================================================
    // SALARY MENU
    // ============================================================

    private void salaryMenu() {

        while (true) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("                SALARY MANAGEMENT");
            System.out.println("==================================================");
            System.out.println("1. View Employee Salary");
            System.out.println("2. Add Salary Record");
            System.out.println("3. Calculate Gross Salary");
            System.out.println("4. Back");
            System.out.println("==================================================");

            int choice =
                    InputUtilPayroll.readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    viewEmployeeSalary();
                    break;

                case 2:
                    addSalaryRecord();
                    break;

                case 3:
                    calculateGrossSalary();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    // ============================================================
    // VIEW SALARY
    // ============================================================

    private void viewEmployeeSalary() {

        String employeeId =
                InputUtilPayroll.readString(
                        "Enter Employee ID: "
                );

        Salary salary =
                payrollService.getCurrentSalary(employeeId);

        if (salary == null) {

            System.out.println("Salary record not found.");
            return;
        }

        System.out.println();
        System.out.println("==================================================");
        System.out.println("              CURRENT SALARY");
        System.out.println("==================================================");

        System.out.println(
                "Salary ID         : " + salary.getSalaryId()
        );

        System.out.println(
                "Employee ID       : " + salary.getEmployeeId()
        );

        System.out.printf(
                "Basic Salary      : %.2f%n",
                salary.getBasicSalary()
        );

        System.out.printf(
                "HRA               : %.2f%n",
                salary.getHra()
        );

        System.out.printf(
                "Allowance         : %.2f%n",
                salary.getAllowance()
        );

        System.out.printf(
                "Gross Salary      : %.2f%n",
                salary.getGrossSalary()
        );

        System.out.println(
                "Effective Date    : " + salary.getEffectiveDate()
        );

        System.out.println("==================================================");
    }


    // ============================================================
    // ADD SALARY RECORD
    // ============================================================

    private void addSalaryRecord() {

        String employeeId =
                InputUtilPayroll.readString(
                        "Enter Employee ID: "
                );

        EmployeeP employeeP =
                payrollService.getEmployeeById(employeeId);

        if (employeeP == null) {

            System.out.println("Employee not found.");
            return;
        }

        double basicSalary =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Basic Salary: "
                );

        double hra =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter HRA: "
                );

        double allowance =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Allowance: "
                );

        LocalDate effectiveDate =
                InputUtilPayroll.readDate(
                        "Enter Effective Date (yyyy-MM-dd): "
                );

        boolean result =
                payrollService.addSalaryRecord(
                        employeeId,
                        basicSalary,
                        hra,
                        allowance,
                        effectiveDate
                );

        if (result) {

            System.out.println(
                    "Salary record added successfully."
            );

        } else {

            System.out.println(
                    "Unable to add salary record."
            );
        }
    }


    // ============================================================
    // CALCULATE GROSS SALARY
    // ============================================================

    private void calculateGrossSalary() {

        double basicSalary =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Basic Salary: "
                );

        double hra =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter HRA: "
                );

        double allowance =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Allowance: "
                );

        double grossSalary =
                payrollService.calculateGrossSalary(
                        basicSalary,
                        hra,
                        allowance
                );

        System.out.printf(
                "Gross Salary: %.2f%n",
                grossSalary
        );
    }


    // ============================================================
    // PAYROLL MENU
    // ============================================================

    private void payrollMenu() {

        while (true) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("                PAYROLL PROCESSING");
            System.out.println("==================================================");
            System.out.println("1. Calculate Payroll");
            System.out.println("2. Process Single Payroll");
            System.out.println("3. Process Payroll in Batch");
            System.out.println("4. View All Payroll");
            System.out.println("5. View EmployeeP Payroll");
            System.out.println("6. Calculate Tax");
            System.out.println("7. Back");
            System.out.println("==================================================");

            int choice =
                    InputUtilPayroll.readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    calculatePayroll();
                    break;

                case 2:
                    processSinglePayroll();
                    break;

                case 3:
                    processBatchPayroll();
                    break;

                case 4:
                    viewAllPayroll();
                    break;

                case 5:
                    viewEmployeePayroll();
                    break;

                case 6:
                    calculateTax();
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    // ============================================================
    // CALCULATE PAYROLL
    // ============================================================

    private void calculatePayroll() {

        String employeeId =
                InputUtilPayroll.readString(
                        "Enter Employee ID: "
                );

        LocalDate payMonth =
                InputUtilPayroll.readDate(
                        "Enter Pay Month (yyyy-MM-dd): "
                );

        double otherDeduction =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Other Deduction: "
                );

        Payroll payroll =
                payrollService.calculatePayroll(
                        employeeId,
                        payMonth,
                        otherDeduction
                );

        if (payroll == null) {

            System.out.println(
                    "Unable to calculate payroll."
            );

            return;
        }

        displayPayroll(payroll);
    }


    // ============================================================
    // PROCESS SINGLE PAYROLL
    // ============================================================

    private void processSinglePayroll() {

        String employeeId =
                InputUtilPayroll.readString(
                        "Enter Employee ID: "
                );

        LocalDate payMonth =
                InputUtilPayroll.readDate(
                        "Enter Pay Month (yyyy-MM-dd): "
                );

        double otherDeduction =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Other Deduction: "
                );

        Payroll payroll =
                payrollService.calculatePayroll(
                        employeeId,
                        payMonth,
                        otherDeduction
                );

        if (payroll == null) {

            System.out.println(
                    "Unable to calculate payroll."
            );

            return;
        }

        boolean result =
                payrollService.savePayroll(payroll);

        if (result) {

            System.out.println(
                    "Payroll processed successfully."
            );

            displayPayroll(payroll);

        } else {

            System.out.println(
                    "Payroll processing failed."
            );
        }
    }


    // ============================================================
    // BATCH PAYROLL
    // ============================================================

    private void processBatchPayroll() {

        List<EmployeeP> employeePS =
                payrollService.getActiveEmployees();

        if (employeePS.isEmpty()) {

            System.out.println(
                    "No active employee available."
            );

            return;
        }

        LocalDate payMonth =
                InputUtilPayroll.readDate(
                        "Enter Pay Month (yyyy-MM-dd): "
                );

        List<Payroll> payrollList =
                new ArrayList<>();

        for (EmployeeP employeeP : employeePS) {

            System.out.println();
            System.out.println(
                    "Employee: "
                            + employeeP.getEmployeeId()
                            + " - "
                            + employeeP.getEmployeeName()
            );

            double otherDeduction =
                    InputUtilPayroll.readNonNegativeDouble(
                            "Enter Other Deduction: "
                    );

            Payroll payroll =
                    payrollService.calculatePayroll(
                            employeeP.getEmployeeId(),
                            payMonth,
                            otherDeduction
                    );

            if (payroll != null) {

                payrollList.add(payroll);
            }
        }

        if (payrollList.isEmpty()) {

            System.out.println(
                    "No payroll records prepared."
            );

            return;
        }

        int processed =
                payrollService.processPayrollBatch(
                        payrollList
                );

        System.out.println(
                "Total payroll records processed: "
                        + processed
        );
    }


    // ============================================================
    // VIEW ALL PAYROLL
    // ============================================================

    private void viewAllPayroll() {

        List<Payroll> payrollList =
                payrollService.getAllPayroll();

        if (payrollList.isEmpty()) {

            System.out.println(
                    "No payroll records found."
            );

            return;
        }

        for (Payroll payroll : payrollList) {

            displayPayroll(payroll);

            System.out.println(
                    "--------------------------------------------------"
            );
        }
    }


    // ============================================================
    // VIEW EMPLOYEE PAYROLL
    // ============================================================

    private void viewEmployeePayroll() {

        String employeeId =
                InputUtilPayroll.readString(
                        "Enter Employee ID: "
                );

        List<Payroll> payrollList =
                payrollService.getPayrollByEmployee(
                        employeeId
                );

        if (payrollList.isEmpty()) {

            System.out.println(
                    "No payroll records found."
            );

            return;
        }

        for (Payroll payroll : payrollList) {

            displayPayroll(payroll);

            System.out.println(
                    "--------------------------------------------------"
            );
        }
    }


    // ============================================================
    // TAX CALCULATION
    // ============================================================

    private void calculateTax() {

        double grossSalary =
                InputUtilPayroll.readNonNegativeDouble(
                        "Enter Gross Salary: "
                );

        double tax =
                payrollService.calculateTax(
                        grossSalary
                );

        double netAfterTax =
                grossSalary - tax;

        System.out.println();
        System.out.printf(
                "Gross Salary : %.2f%n",
                grossSalary
        );

        System.out.printf(
                "Tax          : %.2f%n",
                tax
        );

        System.out.printf(
                "After Tax    : %.2f%n",
                netAfterTax
        );
    }


    // ============================================================
    // PAYSLIP
    // ============================================================

    private void generatePayslip() {

        String employeeId =
                InputUtilPayroll.readString(
                        "Enter Employee ID: "
                );

        Payroll payroll =
                payrollService.getLatestPayroll(
                        employeeId
                );

        if (payroll == null) {

            System.out.println(
                    "No payroll record found for this employee."
            );

            return;
        }

        payrollService.generatePayslip(payroll);
    }


    // ============================================================
    // REPORT MENU
    // ============================================================

    private void reportMenu() {

        while (true) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("                 PAYROLL REPORTS");
            System.out.println("==================================================");
            System.out.println("1. Payroll Statistics");
            System.out.println("2. Department-wise Salary Report");
            System.out.println("3. Stream API EmployeeP Report");
            System.out.println("4. Stream API Payroll Report");
            System.out.println("5. Back");
            System.out.println("==================================================");

            int choice =
                    InputUtilPayroll.readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    payrollService.displayPayrollStatistics();
                    break;

                case 2:
                    payrollService.displayDepartmentWiseSalary();
                    break;

                case 3:
                    payrollService.displayStreamEmployeeReport();
                    break;

                case 4:
                    payrollService.displayStreamPayrollReport();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    // ============================================================
    // DISPLAY EMPLOYEE
    // ============================================================

    private void displayEmployee(EmployeeP employeeP) {

        System.out.println(
                "Employee ID       : " + employeeP.getEmployeeId()
        );

        System.out.println(
                "EmployeeP Name     : " + employeeP.getEmployeeName()
        );

        System.out.println(
                "Department        : " + employeeP.getDepartment()
        );

        System.out.println(
                "Designation       : " + employeeP.getDesignation()
        );

        System.out.println(
                "Email             : " + employeeP.getEmail()
        );

        System.out.println(
                "Phone             : " + employeeP.getPhone()
        );

        System.out.printf(
                "Basic Salary      : %.2f%n",
                employeeP.getBasicSalary()
        );

        System.out.printf(
                "HRA               : %.2f%n",
                employeeP.getHra()
        );

        System.out.printf(
                "Allowance         : %.2f%n",
                employeeP.getAllowance()
        );

        System.out.println(
                "Status            : " + employeeP.getStatus()
        );

        System.out.println(
                "Joining Date      : " + employeeP.getJoiningDate()
        );
    }


    // ============================================================
    // DISPLAY PAYROLL
    // ============================================================

    private void displayPayroll(Payroll payroll) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("                PAYROLL DETAILS");
        System.out.println("==================================================");

        System.out.println(
                "Payroll ID        : " + payroll.getPayrollId()
        );

        System.out.println(
                "Employee ID       : " + payroll.getEmployeeId()
        );

        System.out.println(
                "Pay Month         : " + payroll.getPayMonth()
        );

        System.out.printf(
                "Basic Salary      : %.2f%n",
                payroll.getBasicSalary()
        );

        System.out.printf(
                "HRA               : %.2f%n",
                payroll.getHra()
        );

        System.out.printf(
                "Allowance         : %.2f%n",
                payroll.getAllowance()
        );

        System.out.printf(
                "Gross Salary      : %.2f%n",
                payroll.getGrossSalary()
        );

        System.out.printf(
                "Tax               : %.2f%n",
                payroll.getTax()
        );

        System.out.printf(
                "Other Deduction   : %.2f%n",
                payroll.getOtherDeduction()
        );

        System.out.printf(
                "Total Deduction   : %.2f%n",
                payroll.getTotalDeduction()
        );

        System.out.printf(
                "Net Salary        : %.2f%n",
                payroll.getNetSalary()
        );

        System.out.println(
                "Payment Status    : " + payroll.getPaymentStatus()
        );

        System.out.println(
                "Generated Date    : " + payroll.getGeneratedDate()
        );

        System.out.println("==================================================");
    }
}
