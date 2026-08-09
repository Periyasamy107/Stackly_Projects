package service;

import model.EmployeeP;
import database.DatabaseManagerPayroll;
import model.Payroll;
import model.Salary;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PayrollService {

    // ============================================================
    // EMPLOYEE MANAGEMENT
    // ============================================================

    public boolean registerEmployee(EmployeeP employee) {

        String sql = """
                INSERT INTO employees
                (employee_id, employee_name, department, designation,
                 email, phone, basic_salary, hra, allowance, status, joining_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employee.getEmployeeId());
            statement.setString(2, employee.getEmployeeName());
            statement.setString(3, employee.getDepartment());
            statement.setString(4, employee.getDesignation());
            statement.setString(5, employee.getEmail());
            statement.setString(6, employee.getPhone());
            statement.setDouble(7, employee.getBasicSalary());
            statement.setDouble(8, employee.getHra());
            statement.setDouble(9, employee.getAllowance());
            statement.setString(10, employee.getStatus());
            statement.setDate(11, Date.valueOf(employee.getJoiningDate()));

            int rows = statement.executeUpdate();

            if (rows > 0) {

                // Also create the initial salary record.
                addSalaryRecord(
                        employee.getEmployeeId(),
                        employee.getBasicSalary(),
                        employee.getHra(),
                        employee.getAllowance(),
                        employee.getJoiningDate()
                );

                return true;
            }

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("Employee ID already exists.");

        } catch (SQLException e) {

            System.out.println("Employee registration failed.");
            e.printStackTrace();
        }

        return false;
    }


    public List<EmployeeP> getAllEmployees() {

        List<EmployeeP> employeePS = new ArrayList<>();

        String sql = """
                SELECT employee_id, employee_name, department, designation,
                       email, phone, basic_salary, hra, allowance,
                       status, joining_date
                FROM employees
                ORDER BY employee_id
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                employeePS.add(mapEmployee(resultSet));
            }

        } catch (SQLException e) {

            System.out.println("Unable to retrieve employee.");
            e.printStackTrace();
        }

        return employeePS;
    }


    public EmployeeP getEmployeeById(String employeeId) {

        String sql = """
                SELECT employee_id, employee_name, department, designation,
                       email, phone, basic_salary, hra, allowance,
                       status, joining_date
                FROM employees
                WHERE employee_id = ?
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapEmployee(resultSet);
                }
            }

        } catch (SQLException e) {

            System.out.println("Unable to search employee.");
            e.printStackTrace();
        }

        return null;
    }


    public boolean updateEmployee(EmployeeP employee) {

        String sql = """
                UPDATE employees
                SET employee_name = ?,
                    department = ?,
                    designation = ?,
                    email = ?,
                    phone = ?,
                    basic_salary = ?,
                    hra = ?,
                    allowance = ?,
                    status = ?,
                    joining_date = ?
                WHERE employee_id = ?
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employee.getEmployeeName());
            statement.setString(2, employee.getDepartment());
            statement.setString(3, employee.getDesignation());
            statement.setString(4, employee.getEmail());
            statement.setString(5, employee.getPhone());
            statement.setDouble(6, employee.getBasicSalary());
            statement.setDouble(7, employee.getHra());
            statement.setDouble(8, employee.getAllowance());
            statement.setString(9, employee.getStatus());
            statement.setDate(10, Date.valueOf(employee.getJoiningDate()));
            statement.setString(11, employee.getEmployeeId());

            int rows = statement.executeUpdate();

            if (rows > 0) {

                updateCurrentSalary(
                        employee.getEmployeeId(),
                        employee.getBasicSalary(),
                        employee.getHra(),
                        employee.getAllowance()
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println("Employee update failed.");
            e.printStackTrace();
        }

        return false;
    }


    public boolean deactivateEmployee(String employeeId) {

        String sql = """
                UPDATE employees
                SET status = 'INACTIVE'
                WHERE employee_id = ?
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employeeId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Unable to deactivate employee.");
            e.printStackTrace();
        }

        return false;
    }


    public List<EmployeeP> getActiveEmployees() {

        return getAllEmployees()
                .stream()
                .filter(employee ->
                        employee.getStatus().equalsIgnoreCase("ACTIVE"))
                .collect(Collectors.toList());
    }


    // ============================================================
    // SALARY MANAGEMENT
    // ============================================================

    public boolean addSalaryRecord(String employeeId,
                                   double basicSalary,
                                   double hra,
                                   double allowance,
                                   LocalDate effectiveDate) {

        double grossSalary =
                calculateGrossSalary(basicSalary, hra, allowance);

        String sql = """
                INSERT INTO salaries
                (employee_id, basic_salary, hra, allowance,
                 gross_salary, effective_date)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employeeId);
            statement.setDouble(2, basicSalary);
            statement.setDouble(3, hra);
            statement.setDouble(4, allowance);
            statement.setDouble(5, grossSalary);
            statement.setDate(6, Date.valueOf(effectiveDate));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Unable to add salary record.");
            e.printStackTrace();
        }

        return false;
    }


    public Salary getCurrentSalary(String employeeId) {

        String sql = """
                SELECT salary_id, employee_id, basic_salary,
                       hra, allowance, gross_salary, effective_date
                FROM salaries
                WHERE employee_id = ?
                ORDER BY effective_date DESC, salary_id DESC
                LIMIT 1
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapSalary(resultSet);
                }
            }

        } catch (SQLException e) {

            System.out.println("Unable to retrieve salary.");
            e.printStackTrace();
        }

        return null;
    }


    private boolean updateCurrentSalary(String employeeId,
                                        double basicSalary,
                                        double hra,
                                        double allowance) {

        Salary salary = getCurrentSalary(employeeId);

        if (salary == null) {

            return addSalaryRecord(
                    employeeId,
                    basicSalary,
                    hra,
                    allowance,
                    LocalDate.now()
            );
        }

        double grossSalary =
                calculateGrossSalary(basicSalary, hra, allowance);

        String sql = """
                UPDATE salaries
                SET basic_salary = ?,
                    hra = ?,
                    allowance = ?,
                    gross_salary = ?
                WHERE salary_id = ?
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, basicSalary);
            statement.setDouble(2, hra);
            statement.setDouble(3, allowance);
            statement.setDouble(4, grossSalary);
            statement.setInt(5, salary.getSalaryId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Unable to update salary.");
            e.printStackTrace();
        }

        return false;
    }


    public double calculateGrossSalary(double basicSalary,
                                       double hra,
                                       double allowance) {

        return basicSalary + hra + allowance;
    }


    // ============================================================
    // TAX CALCULATION
    // ============================================================

    public double calculateTax(double grossSalary) {

        if (grossSalary <= 30000) {

            return 0;

        } else if (grossSalary <= 50000) {

            return grossSalary * 0.05;

        } else if (grossSalary <= 75000) {

            return grossSalary * 0.10;

        } else if (grossSalary <= 100000) {

            return grossSalary * 0.15;

        } else {

            return grossSalary * 0.20;
        }
    }


    public double calculateTotalDeduction(double tax,
                                          double otherDeduction) {

        return tax + otherDeduction;
    }


    public double calculateNetSalary(double grossSalary,
                                     double totalDeduction) {

        return grossSalary - totalDeduction;
    }


    // ============================================================
    // PAYROLL PROCESSING
    // ============================================================

    public Payroll calculatePayroll(String employeeId,
                                    LocalDate payMonth,
                                    double otherDeduction) {

        EmployeeP employee = getEmployeeById(employeeId);

        if (employee == null) {
            return null;
        }

        double basicSalary = employee.getBasicSalary();
        double hra = employee.getHra();
        double allowance = employee.getAllowance();

        double grossSalary =
                calculateGrossSalary(
                        basicSalary,
                        hra,
                        allowance
                );

        double tax = calculateTax(grossSalary);

        double totalDeduction =
                calculateTotalDeduction(
                        tax,
                        otherDeduction
                );

        double netSalary =
                calculateNetSalary(
                        grossSalary,
                        totalDeduction
                );

        return new Payroll(
                employeeId,
                payMonth,
                basicSalary,
                hra,
                allowance,
                grossSalary,
                tax,
                otherDeduction,
                totalDeduction,
                netSalary,
                "PROCESSED",
                LocalDate.now()
        );
    }


    public boolean savePayroll(Payroll payroll) {

        String sql = """
                INSERT INTO payroll
                (employee_id, pay_month, basic_salary, hra, allowance,
                 gross_salary, tax, other_deduction, total_deduction,
                 net_salary, payment_status, generated_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setPayrollParameters(statement, payroll);

            return statement.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println(
                    "Payroll already exists or employee does not exist."
            );

        } catch (SQLException e) {

            System.out.println("Unable to save payroll.");
            e.printStackTrace();
        }

        return false;
    }


    private void setPayrollParameters(PreparedStatement statement,
                                      Payroll payroll)
            throws SQLException {

        statement.setString(1, payroll.getEmployeeId());
        statement.setDate(2, Date.valueOf(payroll.getPayMonth()));
        statement.setDouble(3, payroll.getBasicSalary());
        statement.setDouble(4, payroll.getHra());
        statement.setDouble(5, payroll.getAllowance());
        statement.setDouble(6, payroll.getGrossSalary());
        statement.setDouble(7, payroll.getTax());
        statement.setDouble(8, payroll.getOtherDeduction());
        statement.setDouble(9, payroll.getTotalDeduction());
        statement.setDouble(10, payroll.getNetSalary());
        statement.setString(11, payroll.getPaymentStatus());
        statement.setDate(12, Date.valueOf(payroll.getGeneratedDate()));
    }


    // ============================================================
    // JDBC BATCH PROCESSING
    // ============================================================

    public int processPayrollBatch(List<Payroll> payrollList) {

        if (payrollList == null || payrollList.isEmpty()) {
            return 0;
        }

        String sql = """
                INSERT INTO payroll
                (employee_id, pay_month, basic_salary, hra, allowance,
                 gross_salary, tax, other_deduction, total_deduction,
                 net_salary, payment_status, generated_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        int totalProcessed = 0;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);

            try {

                for (Payroll payroll : payrollList) {

                    setPayrollParameters(statement, payroll);

                    statement.addBatch();
                }

                int[] results = statement.executeBatch();

                for (int result : results) {

                    if (result >= 0 ||
                            result == Statement.SUCCESS_NO_INFO) {

                        totalProcessed++;
                    }
                }

                connection.commit();

                System.out.println(
                        totalProcessed +
                                " payroll records processed successfully using batch."
                );

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Batch payroll failed. Transaction rolled back."
                );

                e.printStackTrace();
            }

        } catch (SQLException e) {

            System.out.println("Unable to process payroll batch.");
            e.printStackTrace();
        }

        return totalProcessed;
    }


    // ============================================================
    // PAYROLL RETRIEVAL
    // ============================================================

    public List<Payroll> getAllPayroll() {

        List<Payroll> payrollList = new ArrayList<>();

        String sql = """
                SELECT payroll_id, employee_id, pay_month,
                       basic_salary, hra, allowance, gross_salary,
                       tax, other_deduction, total_deduction,
                       net_salary, payment_status, generated_date
                FROM payroll
                ORDER BY pay_month DESC, employee_id
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                payrollList.add(mapPayroll(resultSet));
            }

        } catch (SQLException e) {

            System.out.println("Unable to retrieve payroll records.");
            e.printStackTrace();
        }

        return payrollList;
    }


    public List<Payroll> getPayrollByEmployee(String employeeId) {

        List<Payroll> payrollList = new ArrayList<>();

        String sql = """
                SELECT payroll_id, employee_id, pay_month,
                       basic_salary, hra, allowance, gross_salary,
                       tax, other_deduction, total_deduction,
                       net_salary, payment_status, generated_date
                FROM payroll
                WHERE employee_id = ?
                ORDER BY pay_month DESC
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    payrollList.add(mapPayroll(resultSet));
                }
            }

        } catch (SQLException e) {

            System.out.println("Unable to retrieve employee payroll.");
            e.printStackTrace();
        }

        return payrollList;
    }


    // ============================================================
    // PAYSLIP
    // ============================================================

    public Payroll getLatestPayroll(String employeeId) {

        String sql = """
                SELECT payroll_id, employee_id, pay_month,
                       basic_salary, hra, allowance, gross_salary,
                       tax, other_deduction, total_deduction,
                       net_salary, payment_status, generated_date
                FROM payroll
                WHERE employee_id = ?
                ORDER BY pay_month DESC, payroll_id DESC
                LIMIT 1
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapPayroll(resultSet);
                }
            }

        } catch (SQLException e) {

            System.out.println("Unable to retrieve latest payroll.");
            e.printStackTrace();
        }

        return null;
    }


    public void generatePayslip(Payroll payroll) {

        if (payroll == null) {

            System.out.println("Payroll record not found.");
            return;
        }

        EmployeeP employee =
                getEmployeeById(payroll.getEmployeeId());

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                         PAYSLIP");
        System.out.println("============================================================");

        System.out.println("EmployeeP ID       : " + payroll.getEmployeeId());

        if (employee != null) {

            System.out.println(
                    "Employee Name     : " + employee.getEmployeeName()
            );

            System.out.println(
                    "Department        : " + employee.getDepartment()
            );

            System.out.println(
                    "Designation       : " + employee.getDesignation()
            );
        }

        System.out.println("Pay Month         : " + payroll.getPayMonth());

        System.out.println("------------------------------------------------------------");
        System.out.println("EARNINGS");
        System.out.println("------------------------------------------------------------");

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

        System.out.println("------------------------------------------------------------");

        System.out.printf(
                "Gross Salary      : %.2f%n",
                payroll.getGrossSalary()
        );

        System.out.println();
        System.out.println("DEDUCTIONS");
        System.out.println("------------------------------------------------------------");

        System.out.printf(
                "Tax               : %.2f%n",
                payroll.getTax()
        );

        System.out.printf(
                "Other Deduction   : %.2f%n",
                payroll.getOtherDeduction()
        );

        System.out.println("------------------------------------------------------------");

        System.out.printf(
                "Total Deduction   : %.2f%n",
                payroll.getTotalDeduction()
        );

        System.out.println("============================================================");

        System.out.printf(
                "Net Salary        : %.2f%n",
                payroll.getNetSalary()
        );

        System.out.println("============================================================");
        System.out.println();
    }


    // ============================================================
    // SQL AGGREGATE FUNCTIONS
    // ============================================================

    public int getTotalEmployees() {

        String sql = "SELECT COUNT(*) FROM employees";

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println("Unable to calculate employee count.");
            e.printStackTrace();
        }

        return 0;
    }


    public double getTotalPayrollAmount() {

        String sql = """
                SELECT COALESCE(SUM(net_salary), 0)
                FROM payroll
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (SQLException e) {

            System.out.println("Unable to calculate total payroll.");
            e.printStackTrace();
        }

        return 0;
    }


    public double getAverageSalary() {

        String sql = """
                SELECT COALESCE(AVG(net_salary), 0)
                FROM payroll
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (SQLException e) {

            System.out.println("Unable to calculate average salary.");
            e.printStackTrace();
        }

        return 0;
    }


    public double getHighestSalary() {

        String sql = """
                SELECT COALESCE(MAX(net_salary), 0)
                FROM payroll
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (SQLException e) {

            System.out.println("Unable to calculate highest salary.");
            e.printStackTrace();
        }

        return 0;
    }


    public double getLowestSalary() {

        String sql = """
                SELECT COALESCE(MIN(net_salary), 0)
                FROM payroll
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (SQLException e) {

            System.out.println("Unable to calculate lowest salary.");
            e.printStackTrace();
        }

        return 0;
    }


    public void displayPayrollStatistics() {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("              PAYROLL STATISTICS");
        System.out.println("==================================================");

        System.out.println(
                "Total Employees       : " + getTotalEmployees()
        );

        System.out.printf(
                "Total Payroll Amount  : %.2f%n",
                getTotalPayrollAmount()
        );

        System.out.printf(
                "Average Net Salary    : %.2f%n",
                getAverageSalary()
        );

        System.out.printf(
                "Highest Net Salary    : %.2f%n",
                getHighestSalary()
        );

        System.out.printf(
                "Lowest Net Salary     : %.2f%n",
                getLowestSalary()
        );

        System.out.println("==================================================");
    }


    // ============================================================
    // DEPARTMENT-WISE AGGREGATE REPORT
    // ============================================================

    public void displayDepartmentWiseSalary() {

        String sql = """
                SELECT e.department,
                       COUNT(p.payroll_id) AS employee_count,
                       SUM(p.net_salary) AS total_salary,
                       AVG(p.net_salary) AS average_salary,
                       MAX(p.net_salary) AS highest_salary,
                       MIN(p.net_salary) AS lowest_salary
                FROM employees e
                INNER JOIN payroll p
                    ON e.employee_id = p.employee_id
                GROUP BY e.department
                ORDER BY e.department
                """;

        try (Connection connection = DatabaseManagerPayroll.getPayrollConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println();
            System.out.println("======================================================================");
            System.out.println("                 DEPARTMENT-WISE PAYROLL REPORT");
            System.out.println("======================================================================");

            System.out.printf(
                    "%-15s %-10s %-15s %-15s %-15s %-15s%n",
                    "Department",
                    "Count",
                    "Total",
                    "Average",
                    "Highest",
                    "Lowest"
            );

            System.out.println("----------------------------------------------------------------------");

            while (resultSet.next()) {

                System.out.printf(
                        "%-15s %-10d %-15.2f %-15.2f %-15.2f %-15.2f%n",
                        resultSet.getString("department"),
                        resultSet.getInt("employee_count"),
                        resultSet.getDouble("total_salary"),
                        resultSet.getDouble("average_salary"),
                        resultSet.getDouble("highest_salary"),
                        resultSet.getDouble("lowest_salary")
                );
            }

            System.out.println("======================================================================");

        } catch (SQLException e) {

            System.out.println(
                    "Unable to generate department-wise report."
            );

            e.printStackTrace();
        }
    }


    // ============================================================
    // JAVA 8 STREAM API REPORTS
    // ============================================================

    public void displayStreamEmployeeReport() {

        List<EmployeeP> employeePS = getAllEmployees();

        if (employeePS.isEmpty()) {

            System.out.println("No employee found.");
            return;
        }

        System.out.println();
        System.out.println("==================================================");
        System.out.println("          STREAM API EMPLOYEE REPORT");
        System.out.println("==================================================");

        long activeEmployees =
                employeePS.stream()
                        .filter(employee ->
                                employee.getStatus()
                                        .equalsIgnoreCase("ACTIVE"))
                        .count();

        System.out.println(
                "Active Employees      : " + activeEmployees
        );


        double averageBasicSalary =
                employeePS.stream()
                        .mapToDouble(EmployeeP::getBasicSalary)
                        .average()
                        .orElse(0);

        System.out.printf(
                "Average Basic Salary  : %.2f%n",
                averageBasicSalary
        );


        EmployeeP highestPaidEmployee =
                employeePS.stream()
                        .max(Comparator.comparingDouble(
                                EmployeeP::getBasicSalary))
                        .orElse(null);

        if (highestPaidEmployee != null) {

            System.out.println(
                    "Highest Basic Salary  : "
                            + highestPaidEmployee.getEmployeeName()
                            + " - "
                            + highestPaidEmployee.getBasicSalary()
            );
        }


        System.out.println();
        System.out.println("Employees by Department:");

        Map<String, List<EmployeeP>> employeesByDepartment =
                employeePS.stream()
                        .collect(Collectors.groupingBy(
                                EmployeeP::getDepartment
                        ));

        employeesByDepartment.forEach(
                (department, employeeList) ->
                        System.out.println(
                                department + " : "
                                        + employeeList.size()
                        )
        );

        System.out.println("==================================================");
    }


    public void displayStreamPayrollReport() {

        List<Payroll> payrollList = getAllPayroll();

        if (payrollList.isEmpty()) {

            System.out.println("No payroll records found.");
            return;
        }

        System.out.println();
        System.out.println("==================================================");
        System.out.println("           STREAM API PAYROLL REPORT");
        System.out.println("==================================================");


        double totalNetSalary =
                payrollList.stream()
                        .mapToDouble(Payroll::getNetSalary)
                        .sum();

        double averageNetSalary =
                payrollList.stream()
                        .mapToDouble(Payroll::getNetSalary)
                        .average()
                        .orElse(0);

        double highestNetSalary =
                payrollList.stream()
                        .mapToDouble(Payroll::getNetSalary)
                        .max()
                        .orElse(0);

        double lowestNetSalary =
                payrollList.stream()
                        .mapToDouble(Payroll::getNetSalary)
                        .min()
                        .orElse(0);


        System.out.printf(
                "Total Net Salary     : %.2f%n",
                totalNetSalary
        );

        System.out.printf(
                "Average Net Salary   : %.2f%n",
                averageNetSalary
        );

        System.out.printf(
                "Highest Net Salary   : %.2f%n",
                highestNetSalary
        );

        System.out.printf(
                "Lowest Net Salary    : %.2f%n",
                lowestNetSalary
        );


        System.out.println();
        System.out.println("Employees sorted by Net Salary:");

        payrollList.stream()
                .sorted(
                        Comparator.comparingDouble(
                                Payroll::getNetSalary
                        ).reversed()
                )
                .forEach(payroll ->
                        System.out.printf(
                                "%-10s %.2f%n",
                                payroll.getEmployeeId(),
                                payroll.getNetSalary()
                        )
                );

        System.out.println("==================================================");
    }


    // ============================================================
    // RESULTSET → MODEL MAPPING
    // ============================================================

    private EmployeeP mapEmployee(ResultSet resultSet)
            throws SQLException {

        return new EmployeeP(
                resultSet.getString("employee_id"),
                resultSet.getString("employee_name"),
                resultSet.getString("department"),
                resultSet.getString("designation"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getDouble("basic_salary"),
                resultSet.getDouble("hra"),
                resultSet.getDouble("allowance"),
                resultSet.getString("status"),
                resultSet.getDate("joining_date").toLocalDate()
        );
    }


    private Salary mapSalary(ResultSet resultSet)
            throws SQLException {

        return new Salary(
                resultSet.getInt("salary_id"),
                resultSet.getString("employee_id"),
                resultSet.getDouble("basic_salary"),
                resultSet.getDouble("hra"),
                resultSet.getDouble("allowance"),
                resultSet.getDouble("gross_salary"),
                resultSet.getDate("effective_date").toLocalDate()
        );
    }


    private Payroll mapPayroll(ResultSet resultSet)
            throws SQLException {

        return new Payroll(
                resultSet.getInt("payroll_id"),
                resultSet.getString("employee_id"),
                resultSet.getDate("pay_month").toLocalDate(),
                resultSet.getDouble("basic_salary"),
                resultSet.getDouble("hra"),
                resultSet.getDouble("allowance"),
                resultSet.getDouble("gross_salary"),
                resultSet.getDouble("tax"),
                resultSet.getDouble("other_deduction"),
                resultSet.getDouble("total_deduction"),
                resultSet.getDouble("net_salary"),
                resultSet.getString("payment_status"),
                resultSet.getDate("generated_date").toLocalDate()
        );
    }
}
