package menu_helper;

import menu.AssignmentMenu;
import service.AssignmentService;
import util.ComplaintDisplayUtil;

import java.util.Scanner;

public class AssignmentMenuHelper {

    private static final AssignmentService assignmentService =
            new AssignmentService();

    public static void assignmentMenu(Scanner scanner) {

        boolean back = false;

        while (!back) {

            try {

                switch (AssignmentMenu.showAssignmentMenu(scanner)) {

                    case 1:
                        assignOfficer(scanner);
                        break;

                    case 2:
                        reassignOfficer(scanner);
                        break;

                    case 3:
                        ComplaintDisplayUtil.displayComplaints(
                                assignmentService
                                        .viewAssignedComplaints()
                        );
                        break;

                    case 4:
                        ComplaintDisplayUtil.displayComplaints(
                                assignmentService
                                        .viewUnassignedComplaints()
                        );
                        break;

                    case 5:
                        back = true;
                        break;

                    default:
                        System.out.println("Invalid choice from assignment menu.");
                }

            } catch (Exception e) {

                System.out.println("Error from Assignment Menu : " + e.getMessage());
            }
        }
    }

    private static void assignOfficer(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        System.out.print("Enter Officer ID (String) : ");
        String officerId = scanner.nextLine();

        if (assignmentService.assignOfficer(
                complaintId,
                officerId)) {

            System.out.println("\nOfficer assigned successfully.\n");

        } else {

            System.out.println("\nOfficer assignment failed.\n");
        }
    }

    private static void reassignOfficer(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        System.out.print("Enter New Officer ID (String) : ");
        String officerId = scanner.nextLine();

        if (assignmentService.reassignOfficer(
                complaintId,
                officerId)) {

            System.out.println("\nOfficer reassigned successfully.\n");

        } else {

            System.out.println("\nOfficer reassignment failed.\n");
        }
    }

}
