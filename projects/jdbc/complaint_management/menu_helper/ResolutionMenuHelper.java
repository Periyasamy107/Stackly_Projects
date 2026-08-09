package menu_helper;

import menu.ResolutionMenu;
import model.Complaint;
import service.ResolutionService;
import util.ComplaintDisplayUtil;

import java.util.List;
import java.util.Scanner;

public class ResolutionMenuHelper {

    private static final ResolutionService resolutionService =
            new ResolutionService();

    public static void resolutionMenu(Scanner scanner) {

        boolean back = false;

        while (!back) {
            try {
                switch (ResolutionMenu.showResolutionMenu(scanner)) {
                    case 1:
                        addResolution(scanner);
                        break;
                    case 2:
                        viewResolution(scanner);
                        break;
                    case 3:
                        markAsResolved(scanner);
                        break;
                    case 4:
                        viewResolvedComplaint(scanner);
                        break;
                    case 5:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        }
    }


    private static void addResolution(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        System.out.print("Enter Resolution (String) : ");
        String resolution = scanner.nextLine();

        if (resolutionService.addResolution(complaintId, resolution)) {
            System.out.println("\nResolution added successfully.\n");
        } else {
            System.out.println("\nFailed to add resolution.\n");
        }
    }


    private static void viewResolution(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        String resolution = resolutionService.viewResolution(complaintId);

        if (resolution == null) {
            System.out.println("\nResolution not available.\n");
        } else {
            System.out.println("\nResolution : " + resolution + "\n");
        }
    }


    private static void markAsResolved(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        System.out.print("Enter Resolution (String) : ");
        String resolution = scanner.nextLine();

        if (resolutionService.markAsResolved(complaintId, resolution)) {

            System.out.println("\nComplaint marked as RESOLVED.\n");
        } else {
            System.out.println("\nFailed to resolve complaint.\n");
        }
    }


    private static void viewResolvedComplaint(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        Complaint complaint = resolutionService.viewResolvedComplaint(complaintId);

        if (complaint == null) {
            System.out.println("\nResolved complaint not found.\n");
        } else {
            ComplaintDisplayUtil.displayComplaints(List.of(complaint));
        }
    }

}
