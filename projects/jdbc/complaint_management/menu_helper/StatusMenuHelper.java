package menu_helper;

import enums.ComplaintStatus;
import menu.StatusMenu;
import service.StatusService;
import util.ComplaintDisplayUtil;

import java.util.Scanner;

public class StatusMenuHelper {

    private static final StatusService statusService =
            new StatusService();

    public static void statusMenu(Scanner scanner) {

        boolean back = false;

        while (!back) {
            try {
                switch (StatusMenu.showStatusMenu(scanner)) {

                    case 1:
                        updateComplaintStatus(scanner);
                        break;
                    case 2:
                        viewComplaintStatus(scanner);
                        break;
                    case 3:
                        ComplaintDisplayUtil.displayComplaints(statusService.viewPendingComplaints());
                        break;
                    case 4:
                        ComplaintDisplayUtil.displayComplaints(statusService.viewComplaintsByStatus(ComplaintStatus.ASSIGNED));
                        break;
                    case 5:
                        ComplaintDisplayUtil.displayComplaints(statusService.viewInProgressComplaints());
                        break;
                    case 6:
                        ComplaintDisplayUtil.displayComplaints(statusService.viewComplaintsByStatus(ComplaintStatus.ON_HOLD));
                        break;
                    case 7:
                        ComplaintDisplayUtil.displayComplaints(statusService.viewResolvedComplaints());
                        break;
                    case 8:
                        ComplaintDisplayUtil.displayComplaints(statusService.viewClosedComplaints());
                        break;
                    case 9:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice for status menu.");
                }
            } catch (Exception e) {
                System.out.println("Error from Status Menu : " + e.getMessage());
            }
        }
    }


    private static void updateComplaintStatus(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        System.out.println();
        System.out.println("Available Statuses:");
        System.out.println("1. ASSIGNED");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. ON_HOLD");
        System.out.println("4. RESOLVED");
        System.out.println("5. CLOSED");

        System.out.print("Enter Status : ");
        String status = scanner.nextLine().toUpperCase();

        try {
            ComplaintStatus complaintStatus = ComplaintStatus.valueOf(status);
            if (statusService.updateStatus(complaintId, complaintStatus)) {
                System.out.println("\nComplaint status updated successfully.\n");
            } else {
                System.out.println("\nComplaint status update failed.\n");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\nInvalid status.\n");
        }
    }


    private static void viewComplaintStatus(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();
        ComplaintStatus status = statusService.viewStatus(complaintId);
        if (status == null) {
            System.out.println("\nComplaint not found.\n");
        } else {
            System.out.println("\nCurrent Status : " + status + "\n");
        }
    }



}
