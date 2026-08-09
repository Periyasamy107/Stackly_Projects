package helper;

import dao.LeaveReportDAO;
import exception.InsufficientLeaveBalanceException;
import exception.LeaveRequestNotFoundException;
import model.LeaveBalance;
import model.LeaveReport;
import model.LeaveRequest;
import model.Manager;
import service.LeaveBalanceService;
import service.LeaveRequestService;
import service.ManagerService;
import util.DateUtil;
import util.ValidationUtilEmp;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ManagerMenuHelper {

    public static void managerMenu(Connection connection, Scanner scanner)
            throws InsufficientLeaveBalanceException, LeaveRequestNotFoundException {


        System.out.println();

        while(true) {

            System.out.println("1. Register Manager");
            System.out.println("2. View All Managers");
            System.out.println("3. Search Manager");
            System.out.println("4. Update Manager");
            System.out.println("5. Deactivate Manager");
            System.out.println("6. View Pending Leave Requests");
            System.out.println("7. View Individual Leave Request");
            System.out.println("8. Approve Leave");
            System.out.println("9. Reject Leave");
            System.out.println("10. View EmployeeP Leave Balance");
            System.out.println("11. View Leave Reports");
            System.out.println("12. Back");
            System.out.print("Please enter the choice from 1 to 12 only : ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("======================================================");
                System.out.println("\nPlease enter a numbers only from 1 to 12.\n");
                System.out.println("======================================================");
                continue;
            }

            switch (choice) {
                case 1:
                    registerManager(scanner);
                    break;
                case 2:
                    viewAllManagers();
                    break;
                case 3:
                    searchManager(scanner);
                    break;
                case 4:
                    updateManager(scanner);
                    break;
                case 5:
                    deactivatedManager(scanner);
                    break;
                case 6:
                    viewPendingLeaveRequests();
                    break;
                case 7:
                    viewLeaveRequest(connection, scanner);
                    break;
                case 8:
                    approveLeave(connection, scanner);
                    break;
                case 9:
                    rejectLeave(connection, scanner);
                    break;
                case 10:
                    viewLeaveBalance(scanner);
                    break;
                case 11:
                    viewLeaveReports(scanner);
                    break;
                case 12:
                    System.out.println("=====================================================");
                    System.out.println("\nBack to the main menu from Manager\n");
                    System.out.println("=====================================================");
                    return;
                default:
                    System.out.println("=====================================================");
                    System.out.println("\nPlease the manager option from 1 to 12.\n");
                    System.out.println("=====================================================");
            }
        }
    }


    public static void registerManager(Scanner scanner) {
        ManagerService managerService = new ManagerService();
        System.out.print("\nEnter Manager ID (String) : ");
        String managerId = scanner.nextLine();

        System.out.print("Enter Manager Name (String) : ");
        String managerName = scanner.nextLine();

        System.out.print("Enter Email (String) : ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone (10 digits) (String) : ");
        String phone = scanner.nextLine();

        System.out.print("Enter Department (String) : ");
        String department = scanner.nextLine();

        System.out.print("Enter Designation (String) : ");
        String designation = scanner.nextLine();

        System.out.print("Enter Joining Date (yyyy-MM-dd): ");
        LocalDate joiningDate = DateUtil.readDate(scanner.nextLine());

        Manager manager = new Manager();

        manager.setManagerId(managerId);
        manager.setManagerName(managerName);
        manager.setEmail(email);
        manager.setPhone(phone);
        manager.setDepartment(department);
        manager.setDesignation(designation);
        manager.setJoiningDate(joiningDate);
        manager.setStatus("ACTIVE"); // Automatically assigned by the application


        if(!ValidationUtilEmp.isNotEmpty(managerId)) {
            System.out.println("============================================");
            System.out.println("\nManager ID cannot be empty.\n");
            System.out.println("============================================");
            return;
        }

        if(!ValidationUtilEmp.isValidName(managerName)) {
            System.out.println("============================================");
            System.out.println("\nInvalid manager name.\n");
            System.out.println("============================================");
            return;
        }

        if(!ValidationUtilEmp.isValidEmail(email)) {
            System.out.println("============================================");
            System.out.println("\nInvalid email address.\n");
            System.out.println("============================================");
            return;
        }

        if(!ValidationUtilEmp.isValidPhone(phone)) {
            System.out.println("============================================");
            System.out.println("\nPhone number must contain 10 digits.\n");
            System.out.println("============================================");
            return;
        }

        if(!ValidationUtilEmp.isNotEmpty(department)) {
            System.out.println("============================================");
            System.out.println("\nManager department cannot be empty.\n");
            System.out.println("============================================");
            return;
        }

        if(!ValidationUtilEmp.isNotEmpty(designation)) {
            System.out.println("============================================");
            System.out.println("\nManager designation cannot be empty.\n");
            System.out.println("============================================");
            return;
        }

        boolean registered = managerService.registerManager(manager);

        if(registered) {
            System.out.println("============================================");
            System.out.println("\nManager Registered Successfully.\n");
            System.out.println("============================================");
        } else {
            System.out.println("============================================");
            System.out.println("\nManager Registration Failed.\n");
            System.out.println("============================================");
        }
    }


    private static void viewAllManagers() {

        ManagerService managerService = new ManagerService();

        List<Manager> managers = managerService.getAllManagers();
        System.out.println("\n==============================================================");
        System.out.println("                     ALL MANAGERS");
        System.out.println("==============================================================");
        if (managers.isEmpty()) {
            System.out.println("No managers found.");
            return;
        }

        for (Manager manager : managers) {
            System.out.println("----------------------------------------------");
            System.out.println("Manager ID    : " + manager.getManagerId());
            System.out.println("Name          : " + manager.getManagerName());
            System.out.println("Email         : " + manager.getEmail());
            System.out.println("Phone         : " + manager.getPhone());
            System.out.println("Department    : " + manager.getDepartment());
            System.out.println("Designation   : " + manager.getDesignation());
            System.out.println("Joining Date  : " + manager.getJoiningDate());
            System.out.println("Status        : " + manager.getStatus());
            System.out.println("Created Date  : " + manager.getCreatedAt());
        }
        System.out.println("==============================================================\n");
    }


    private static void searchManager(Scanner scanner) {
        System.out.print("Enter Manager ID (String) : ");

        String managerId = scanner.nextLine();
        ManagerService managerService = new ManagerService();
        Manager manager = managerService.getManagerById(managerId);

        if (manager == null) {
            System.out.println("\nManager not found : " + manager.getManagerName() + "\n");
        }

        System.out.println("\n==============================================");
        System.out.println("               MANAGER DETAILS");
        System.out.println("==============================================");

        System.out.println("Manager ID   : " + manager.getManagerId());
        System.out.println("Name         : " + manager.getManagerName());
        System.out.println("Email        : " + manager.getEmail());
        System.out.println("Phone        : " + manager.getPhone());
        System.out.println("Department   : " + manager.getDepartment());
        System.out.println("Designation  : " + manager.getDesignation());
        System.out.println("Joining Date : " + manager.getJoiningDate());
        System.out.println("Status       : " + manager.getStatus());
        System.out.println("Created Date : " + manager.getCreatedAt());

        System.out.println("==============================================\n");

    }


    private static void updateManager(Scanner scanner) {

        System.out.print("Enter Manager ID (String) : ");

        String managerId = scanner.nextLine();
        ManagerService managerService = new ManagerService();
        Manager manager = managerService.getManagerById(managerId);

        if (manager == null) {
            System.out.println("============================================");
            System.out.println("\nManager not found.\n");
            System.out.println("============================================");
            return;
        }

        System.out.print("\nEnter Manager Name (String) : ");
        manager.setManagerName(scanner.nextLine());

        System.out.print("Enter Email (String) : ");
        manager.setEmail(scanner.nextLine());

        System.out.print("Enter Phone (10 digits) (String) : ");
        manager.setPhone(scanner.nextLine());

        System.out.print("Enter Department (String) : ");
        manager.setDepartment(scanner.nextLine());

        System.out.print("Enter Designation (String) : ");
        manager.setDesignation(scanner.nextLine());

        System.out.print("Enter Joining Date (yyyy-MM-dd): ");

        manager.setJoiningDate(DateUtil.readDate(scanner.nextLine()));

        System.out.print("Enter Status (ACTIVE/INACTIVE): ");
        manager.setStatus(scanner.nextLine());

        boolean updated = managerService.updateManager(manager);

        if (updated) {
            System.out.println("================================================");
            System.out.println("\nManager updated successfully.\n");
            System.out.println("================================================");

        } else {
            System.out.println("================================================");
            System.out.println("\nManager update failed.\n");
            System.out.println("================================================");
        }
    }


    private static void deactivatedManager(Scanner scanner) {

        System.out.print("Enter Manager ID (String) : ");

        String managerId = scanner.nextLine();
        ManagerService managerService = new ManagerService();
        boolean deleted = managerService.deactivatedManager(managerId);

        if (deleted) {
            System.out.println("============================================");
            System.out.println("\nManager deactivated successfully.\n");
            System.out.println("============================================");

        } else {
            System.out.println("============================================");
            System.out.println("\nManager deactivation failed.\n");
            System.out.println("============================================");
        }
    }


    private static void viewPendingLeaveRequests() {

        LeaveRequestService leaveRequestService = new LeaveRequestService();
        List<LeaveRequest> requests = leaveRequestService.getPendingLeaveRequests();

        System.out.println("==============================================================");
        System.out.println("                 PENDING LEAVE REQUESTS");
        System.out.println("==============================================================");

        if (requests.isEmpty()) {
            System.out.println("==============================================================");
            System.out.println("\nNo pending leave requests.\n");
            System.out.println("==============================================================");
            return;
        }

        for (LeaveRequest request : requests) {
            System.out.println("----------------------------------------------");
            System.out.println("Request ID    : " + request.getRequestId());
            System.out.println("EmployeeP ID   : " + request.getEmployeeId());
            System.out.println("Leave Type    : " + request.getLeaveType());
            System.out.println("Start Date    : " + request.getStartDate());
            System.out.println("End Date      : " + request.getEndDate());
            System.out.println("Number of Days: " + request.getNumberOfDays());
            System.out.println("Reason        : " + request.getReason());
            System.out.println("Status        : " + request.getStatus());
            System.out.println("Applied Date  : " + request.getAppliedDate());
        }

        System.out.println("==============================================================");
    }



    private static void viewLeaveRequest(Connection connection, Scanner scanner) throws LeaveRequestNotFoundException {

        System.out.print("Enter Leave Request ID (int) : ");

        int requestId;

        try {
            requestId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("==============================================================");
            System.out.println("\nInvalid request ID.\n");
            System.out.println("==============================================================");
            return;
        }

        LeaveRequestService leaveRequestService = new LeaveRequestService();
        LeaveRequest request = leaveRequestService.getLeaveRequestById(connection, requestId);

        if (request == null) {
            System.out.println("\nLeave Request Not Found\n");
        }

        System.out.println("==============================================");
        System.out.println("             LEAVE REQUEST DETAILS");
        System.out.println("==============================================");

        System.out.println("Request ID      : " + request.getRequestId());
        System.out.println("EmployeeP ID     : " + request.getEmployeeId());
        System.out.println("Leave Type      : " + request.getLeaveType());
        System.out.println("Start Date      : " + request.getStartDate());
        System.out.println("End Date        : " + request.getEndDate());
        System.out.println("Number of Days  : " + request.getNumberOfDays());
        System.out.println("Reason          : " + request.getReason());
        System.out.println("Status          : " + request.getStatus());
        System.out.println("Applied Date    : " + request.getAppliedDate());
        System.out.println("Approved Date   : " + request.getApprovedDate());
        System.out.println("Approved By     : " + request.getApprovedBy());

        System.out.println("==============================================\n");
    }



    private static void approveLeave(Connection connection, Scanner scanner)
            throws InsufficientLeaveBalanceException {

        System.out.print("Enter Leave Request ID (int) : ");

        int requestId;

        try {
            requestId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("==============================================================");
            System.out.println("\nInvalid request ID.\n");
            System.out.println("==============================================================");
            return;
        }

        System.out.print("Enter Manager ID (String) : ");

        String managerId = scanner.nextLine();
        LeaveBalanceService leaveService = new LeaveBalanceService();
        boolean approved = leaveService.approveLeave(connection, requestId, managerId);

        if (approved) {
            System.out.println("==============================================================");
            System.out.println("\nLeave approved successfully.\n");
            System.out.println("==============================================================");

        } else {
            System.out.println("==============================================================");
            System.out.println("\nLeave approval failed.\n");
            System.out.println("==============================================================");
        }
    }




    private static void rejectLeave(Connection connection, Scanner scanner) {

        System.out.print("Enter Leave Request ID (int) : ");

        int requestId;

        try {
            requestId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("==============================================================");
            System.out.println("\nInvalid request ID.\n");
            System.out.println("==============================================================");
            return;
        }

        System.out.print("Enter Manager ID (String) : ");

        String managerId = scanner.nextLine();
        LeaveBalanceService leaveService = new LeaveBalanceService();
        boolean rejected = leaveService.rejectLeave(connection, requestId, managerId);

        if (rejected) {
            System.out.println("==============================================================");
            System.out.println("\nLeave rejected successfully.\n");
            System.out.println("==============================================================");
        } else {
            System.out.println("==============================================================");
            System.out.println("\nLeave rejection failed.\n");
            System.out.println("==============================================================");
        }
    }




    private static void viewLeaveBalance(Scanner scanner) {

        System.out.print("Enter EmployeeP ID (String) : ");

        String employeeId = scanner.nextLine();
        LeaveBalanceService leaveBalanceService = new LeaveBalanceService();
        List<LeaveBalance> balances = leaveBalanceService.getEmployeeBalances(employeeId);

        if (balances.isEmpty()) {
            System.out.println("==============================================================");
            System.out.println("\nNo leave balance found for employee.\n");
            System.out.println("==============================================================");
            return;
        }

        System.out.println();

        System.out.println("EmployeeP ID : " + employeeId);
        System.out.println("--------------------------------------------------------------");
        System.out.printf("%-15s %-12s %-10s %-12s%n", "Leave Type", "Allocated", "Used", "Available");
        System.out.println("--------------------------------------------------------------");

        for (LeaveBalance balance : balances) {
            System.out.printf(
                    "%-15s %-12d %-10d %-12d%n",
                    balance.getLeaveType(),
                    balance.getAllocatedDays(),
                    balance.getUsedDays(),
                    balance.getAvailableDays()
            );
        }
        System.out.println("--------------------------------------------------------------");

        int totalAllocated = leaveBalanceService.getTotalAllocatedDays(employeeId);
        int totalUsed = leaveBalanceService.getTotalUsedDays(employeeId);
        int totalAvailable = leaveBalanceService.getTotalAvailableDays(employeeId);

        System.out.printf("%-15s %-12d %-10d %-12d%n", "TOTAL", totalAllocated, totalUsed, totalAvailable);
        System.out.println("--------------------------------------------------------------\n");

    }

    public static void viewLeaveReports(Scanner scanner) {
        while (true) {
            System.out.println("==============================================");
            System.out.println("                LEAVE REPORTS");
            System.out.println("==============================================");
            System.out.println("1. All Leave Requests");
            System.out.println("2. EmployeeP Leave Requests");
            System.out.println("3. Back");
            System.out.println("==============================================");
            System.out.print("Enter your choice from 1 to 3 only : ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("=====================================================");
                System.out.println("\nPlease enter a numbers only (1,2,3)\n");
                System.out.println("=====================================================");
                continue;
            }

            switch (choice) {
                case 1:
                    viewAllLeaveReports();
                    break;
                case 2:
                    viewEmployeeLeaveReports(scanner);
                    break;
                case 3:
                    System.out.println("=====================================================");
                    System.out.println("\nBack to Manager menu from report.\n");
                    System.out.println("=====================================================");
                    return;
                default:
                    System.out.println("=====================================================");
                    System.out.println("\nInvalid choice. Enter 1/2/3 only.\n");
                    System.out.println("=====================================================");
            }
        }

    }

    public static void viewAllLeaveReports() {

        List<LeaveReport> reports = LeaveReportDAO.getAllLeaveReports();

        if (reports.isEmpty()) {
            System.out.println("===================================================================");
            System.out.println("\nNo leave reports found.\n");
            System.out.println("===================================================================");
        }

        System.out.println("==============================================================================================================");
        System.out.printf("%-10s %-20s %-8s %-12s %-12s %-12s %-6s %-10s%n",
                "Emp ID",
                "EmployeeP Name",
                "Req ID",
                "Leave Type",
                "Start Date",
                "End Date",
                "Days",
                "Status");
        System.out.println("==============================================================================================================");

        for (LeaveReport report : reports) {

            System.out.printf("%-10s %-20s %-8d %-12s %-12s %-12s %-6d %-10s%n",
                    report.getEmployeeId(),
                    report.getEmployeeName(),
                    report.getRequestId(),
                    report.getLeaveType(),
                    report.getStartDate(),
                    report.getEndDate(),
                    report.getNumberOfDays(),
                    report.getStatus());
        }
        System.out.println("==============================================================================================================\n");
    }



    public static void viewEmployeeLeaveReports(Scanner scanner) {

        System.out.print("Enter the employee id: ");
        String employeeId = scanner.nextLine();

        List<LeaveReport> reports = LeaveReportDAO.getLeaveReportsByEmployee(employeeId);

        if (reports.isEmpty()) {
            System.out.println("===================================================================");
            System.out.println("\nNo leave records found for EmployeeP ID: " + employeeId + "\n");
            System.out.println("===================================================================");
        } else {
            System.out.println("==============================================================================================================");
            System.out.printf("%-10s %-20s %-8s %-12s %-12s %-12s %-6s %-10s%n",
                    "Emp ID",
                    "EmployeeP Name",
                    "Req ID",
                    "Leave Type",
                    "Start Date",
                    "End Date",
                    "Days",
                    "Status");
            System.out.println("==============================================================================================================");
            for (LeaveReport report : reports) {

                System.out.printf("%-10s %-20s %-8d %-12s %-12s %-12s %-6d %-10s%n",
                        report.getEmployeeId(),
                        report.getEmployeeName(),
                        report.getRequestId(),
                        report.getLeaveType(),
                        report.getStartDate(),
                        report.getEndDate(),
                        report.getNumberOfDays(),
                        report.getStatus());
            }
            System.out.println("==============================================================================================================\n");
        }

    }


}
