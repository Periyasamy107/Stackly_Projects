package helper;

import enums.LeaveType;
import exception.*;
import model.Employee;
import model.LeaveBalance;
import model.LeaveRequest;
import service.EmployeeService;
import service.LeaveBalanceService;
import service.LeaveRequestService;
import util.DateUtil;
import util.ValidationUtilEmp;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenuHelper {

    public static void employeeMenu(Connection connection, Scanner scanner)
        throws EmployeeNotFoundException, ManagerNotFoundException, EmployeeAlreadyExistsException {

        System.out.println();

        while(true) {

            System.out.println("1. Register Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Deactivated Employees");
            System.out.println("6. Apply Leave");
            System.out.println("7. View Leave Balance");
            System.out.println("8. View Leave Status");
            System.out.println("9. Cancel Leave");
            System.out.println("10. Back");
            System.out.print("Please enter the choice from 1 to 10 only : ");

            int choice;

            try{
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("======================================================");
                System.out.println("\nPlease enter a number from 1 to 10 only.\n");
                System.out.println("======================================================");
                continue;
            }


            switch (choice) {
                case 1:
                    registerEmployee(scanner);
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    searchEmployee(scanner);
                    break;
                case 4:
                    updateEmployee(scanner);
                    break;
                case 5:
                    deactiveEmployee(scanner);
                    break;
                case 6:
                    applyLeave(connection, scanner);
                    break;
                case 7:
                    viewLeaveBalance(scanner);
                    break;
                case 8:
                    viewLeaveStatus(connection, scanner);
                    break;
                case 9:
                    cancelLeave(connection, scanner);
                    break;
                case 10:
                    System.out.println("=====================================================");
                    System.out.println("\nBack to the main menu from Employee\n");
                    System.out.println("=====================================================");
                    return;
                default:
                    System.out.println("=====================================================");
                    System.out.println("\nPlease enter the employee option from 1 to 10 only.\n");
                    System.out.println("=====================================================");
            }
        }
    }


    private static void registerEmployee(Scanner scanner) {

        System.out.print("\nEnter Employee ID (String) : ");
        String employeeId = scanner.nextLine();

        System.out.print("Enter Employee Name (String) : ");
        String employeeName = scanner.nextLine();

        System.out.print("Enter Email (String) : ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone (10-digits) (String) : ");
        String phone = scanner.nextLine();

        System.out.print("Enter Department (String) : ");
        String department = scanner.nextLine();

        System.out.print("Enter Designation (String) : ");
        String designation = scanner.nextLine();

        System.out.print("Enter Joining Date (yyyy-MM-dd): ");
        LocalDate joiningDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter Manager ID (String) : ");
        String managerId = scanner.nextLine();

        Employee employee = new Employee();

        employee.setEmployeeId(employeeId);
        employee.setEmployeeName(employeeName);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setDepartment(department);
        employee.setDesignation(designation);
        employee.setJoiningDate(joiningDate);
        employee.setManagerId(managerId);

        // Automatically assigned
        employee.setStatus("ACTIVE");

        EmployeeService employeeService = new EmployeeService();

        if(!ValidationUtilEmp.isNotEmpty(employeeId)) {
            System.out.println("==================================================");
            System.out.println("\nEmployee ID cannot be empty.\n");
            System.out.println("==================================================");
            return;
        }

        if(!ValidationUtilEmp.isValidName(employeeName)) {
            System.out.println("==================================================");
            System.out.println("\nInvalid employee name.\n");
            System.out.println("==================================================");
            return;
        }

        if(!ValidationUtilEmp.isValidEmail(email)) {
            System.out.println("==================================================");
            System.out.println("\nInvalid email address.\n");
            System.out.println("==================================================");
            return;
        }

        if(!ValidationUtilEmp.isValidPhone(phone)) {
            System.out.println("==================================================");
            System.out.println("\nPhone number must contain 10 digits.\n");
            System.out.println("==================================================");
            return;
        }

        if(!ValidationUtilEmp.isNotEmpty(department)) {
            System.out.println("==================================================");
            System.out.println("\nEmployee department cannot be empty.\n");
            System.out.println("==================================================");
            return;
        }

        if(!ValidationUtilEmp.isNotEmpty(designation)) {
            System.out.println("==================================================");
            System.out.println("\nEmployee designation cannot be empty.\n");
            System.out.println("==================================================");
            return;
        }

        boolean registered = employeeService.registerEmployee(employee);

        if (registered) {
            System.out.println("==================================================");
            System.out.println("\nEmployee registered successfully.\n");
            System.out.println("==================================================");
        } else {
            System.out.println("==================================================");
            System.out.println("\nEmployee registration failed.\n");
            System.out.println("==================================================");
        }
    }

    private static void viewAllEmployees() {

        EmployeeService employeeService = new EmployeeService();
        List<Employee> employees = employeeService.getAllEmployees();

        System.out.println("==============================================================");
        System.out.println("                    ALL EMPLOYEES");
        System.out.println("==============================================================");

        if (employees.isEmpty()) {
            System.out.println("==================================================");
            System.out.println("\nNo employees found.\n");
            System.out.println("==================================================");
            return;
        } else {
            for (Employee employee : employees) {

                System.out.println("----------------------------------------------");

                System.out.println("Employee ID   : " + employee.getEmployeeId());
                System.out.println("Name          : " + employee.getEmployeeName());
                System.out.println("Email         : " + employee.getEmail());
                System.out.println("Phone         : " + employee.getPhone());
                System.out.println("Department    : " + employee.getDepartment());
                System.out.println("Designation   : " + employee.getDesignation());
                System.out.println("Joining Date  : " + employee.getJoiningDate());
                System.out.println("Manager ID    : " + employee.getManagerId());
                System.out.println("Status        : " + employee.getStatus());
                System.out.println("Created Date  : " + employee.getCreatedAt());
            }

            System.out.println("==============================================================");
        }
    }


    private static void searchEmployee(Scanner scanner) {

        System.out.print("Enter Employee ID (String) : ");

        String employeeId = scanner.nextLine();
        EmployeeService employeeService = new EmployeeService();
        Employee employee = employeeService.getEmployeeById(employeeId);

        if (employee == null) {
            System.out.println("==================================================");
            System.out.println("\nEmployee not found.\n");
            System.out.println("==================================================");
            return;
        }

        System.out.println();
        System.out.println("==============================================");
        System.out.println("              EMPLOYEE DETAILS");
        System.out.println("==============================================");

        System.out.println("Employee ID  : " + employee.getEmployeeId());
        System.out.println("Name         : " + employee.getEmployeeName());
        System.out.println("Email        : " + employee.getEmail());
        System.out.println("Phone        : " + employee.getPhone());
        System.out.println("Department   : " + employee.getDepartment());
        System.out.println("Designation  : " + employee.getDesignation());
        System.out.println("Joining Date : " + employee.getJoiningDate());
        System.out.println("Manager ID   : " + employee.getManagerId());
        System.out.println("Status       : " + employee.getStatus());
        System.out.println("Created Date : " + employee.getCreatedAt());

        System.out.println("==============================================");
    }


    private static void updateEmployee(Scanner scanner) throws EmployeeNotFoundException, ManagerNotFoundException {

        System.out.print("Enter Employee ID (String) : ");

        String employeeId = scanner.nextLine();
        EmployeeService employeeService = new EmployeeService();
        Employee employee = employeeService.getEmployeeById(employeeId);

        if (employee == null) {
            System.out.println("==================================================");
            System.out.println("\nEmployee not found.\n");
            System.out.println("==================================================");
            return;
        }

        System.out.print("\nEnter Employee Name (String) : ");
        employee.setEmployeeName(scanner.nextLine());

        System.out.print("Enter Email (String) : ");
        employee.setEmail(scanner.nextLine());

        System.out.print("Enter Phone (10-digits) (String) : ");
        employee.setPhone(scanner.nextLine());

        System.out.print("Enter Department (String) : ");
        employee.setDepartment(scanner.nextLine());

        System.out.print("Enter Designation (String) : ");
        employee.setDesignation(scanner.nextLine());

        System.out.print("Enter Joining Date (yyyy-MM-dd): ");

        employee.setJoiningDate(LocalDate.parse(scanner.nextLine()));

        System.out.print("Enter Manager ID (String) : ");
        employee.setManagerId(scanner.nextLine());

        System.out.print("Enter Status (ACTIVE/INACTIVE): ");
        employee.setStatus(scanner.nextLine());

        boolean updated = employeeService.updateEmployee(employee);

        if (updated) {
            System.out.println("==================================================");
            System.out.println("\nEmployee updated successfully.\n");
            System.out.println("==================================================");

        } else {
            System.out.println("==================================================");
            System.out.println("\nEmployee update failed.\n");
            System.out.println("==================================================");
        }
    }



    private static void deactiveEmployee(Scanner scanner) throws EmployeeNotFoundException {

        System.out.print("Enter Employee ID (String) : ");

        String employeeId = scanner.nextLine();
        EmployeeService employeeService = new EmployeeService();
        boolean deleted = employeeService.deactiveEmployee(employeeId);

        if (deleted) {
            System.out.println("==================================================");
            System.out.println("\nEmployee deactivated successfully.\n");
            System.out.println("==================================================");

        } else {
            System.out.println("==================================================");
            System.out.println("\nEmployee deactivation failed.\n");
            System.out.println("==================================================");
        }
    }


    private static void applyLeave(Connection connection, Scanner scanner) {
        try{
            System.out.println("==============================================");
            System.out.println("                APPLY LEAVE");
            System.out.println("==============================================");

            System.out.print("Enter Employee ID (String) : ");
            String employeeId = scanner.nextLine();

            System.out.println();
            System.out.println("Leave Types:");
            System.out.println("1. CASUAL");
            System.out.println("2. SICK");
            System.out.println("3. EARNED");

            System.out.print("Select Leave Type: ");

            int leaveChoice = Integer.parseInt(scanner.nextLine());
            LeaveType leaveType;

            switch (leaveChoice) {
                case 1:
                    leaveType = LeaveType.CASUAL;
                    break;
                case 2:
                    leaveType = LeaveType.SICK;
                    break;
                case 3:
                    leaveType = LeaveType.EARNED;
                    break;
                default:
                    System.out.println("==============================================");
                    System.out.println("\nInvalid leave type.\n");
                    System.out.println("==============================================");
                    return;
            }

            System.out.print("Enter Start Date (yyyy-MM-dd): ");
            LocalDate startDate = DateUtil.readDate(scanner.nextLine());

            System.out.print("Enter End Date (yyyy-MM-dd): ");
            LocalDate endDate = DateUtil.readDate(scanner.nextLine());

            System.out.print("Enter Reason (String) : ");
            String reason = scanner.nextLine();

            LeaveRequest leaveRequest = new LeaveRequest();

            leaveRequest.setEmployeeId(employeeId);
            leaveRequest.setLeaveType(leaveType);
            leaveRequest.setStartDate(startDate);
            leaveRequest.setEndDate(endDate);
            leaveRequest.setReason(reason);

            LeaveRequestService leaveService = new LeaveRequestService();
            boolean applied = leaveService.applyLeave(connection, leaveRequest);

            if (applied) {
                System.out.println("==================================================");
                System.out.println("\nLeave applied successfully.");
                System.out.println("Status: PENDING\n");
                System.out.println("==================================================");

            } else {
                System.out.println("==================================================");
                System.out.println("\nLeave application failed.\n");
                System.out.println("==================================================");
            }
        } catch (InvalidLeaveRequestException ex) {
            System.out.println("==================================================");
            System.out.println("\nTry Again...\n");
            System.out.println("==================================================");
        }
    }



    private static void viewLeaveBalance(Scanner scanner) {

        System.out.print("Enter Employee ID (String) : ");

        String employeeId = scanner.nextLine();
        LeaveBalanceService leaveBalanceService = new LeaveBalanceService();
        List<LeaveBalance> balances = leaveBalanceService.getEmployeeBalances(employeeId);


        if (balances.isEmpty()) {
            System.out.println("==================================================");
            System.out.println("\nNo leave balance found.\n");
            System.out.println("==================================================");
            return;
        }

        System.out.println("Employee ID : " + employeeId);
        System.out.println("----------------------------------------------");

        for (LeaveBalance balance : balances) {
            System.out.println("Leave Type      : " + balance.getLeaveType());
            System.out.println("Allocated Days  : " + balance.getAllocatedDays());
            System.out.println("Used Days       : " + balance.getUsedDays());
            System.out.println("Available Days  : " + balance.getAvailableDays());
            System.out.println("----------------------------------------------");
        }
    }


    private static void cancelLeave(Connection connection, Scanner scanner) {

        System.out.print("Enter Leave Request ID (int) : ");

        int requestId = Integer.parseInt(scanner.nextLine());
        LeaveBalanceService leaveService = new LeaveBalanceService();
        boolean cancelled = leaveService.cancelLeave(connection, requestId);

        if (cancelled) {
            System.out.println("==================================================");
            System.out.println("\nLeave cancelled successfully.\n");
            System.out.println("==================================================");

        } else {
            System.out.println("==================================================");
            System.out.println("\nLeave cancellation failed.\n");
            System.out.println("==================================================");
        }
    }


    public static void viewLeaveStatus(Connection connection, Scanner scanner) {
        System.out.print("Enter the employee ID (String) : ");
        String employeeId = scanner.nextLine();

        LeaveRequestService leaveRequestService = new LeaveRequestService();

        List<LeaveRequest> leavesByEmployees = leaveRequestService.getLeavesByEmployeeID(connection, employeeId);

        System.out.println("\n==============================================================");
        System.out.println("                  ALL LEAVES FOR AN EMPLOYEE");
        System.out.println("==============================================================");

        if (leavesByEmployees.isEmpty()) {
            System.out.println("==================================================");
            System.out.println("\nNo leaves found.\n");
            System.out.println("==================================================");
            return;
        }
        for (LeaveRequest leave : leavesByEmployees) {
            System.out.println("----------------------------------------------");
            System.out.println("Request ID     : " + leave.getRequestId());
            System.out.println("Employee ID    : " + leave.getEmployeeId());
            System.out.println("Leave Type     : " + leave.getLeaveType());
            System.out.println("Start Date     : " + leave.getStartDate());
            System.out.println("End Date       : " + leave.getEndDate());
            System.out.println("Number of Days : " + leave.getNumberOfDays());
            System.out.println("Reason         : " + leave.getReason());
            System.out.println("Status         : " + leave.getStatus());
            System.out.println("Approved By    : " + leave.getApprovedBy());
        }
        System.out.println("==============================================================\n");

    }

}
