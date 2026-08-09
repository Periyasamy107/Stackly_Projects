package menu_helper;

import enums.ComplaintCategory;
import enums.ComplaintStatus;
import menu.ComplaintMenu;
import model.Complaint;
import service.ComplaintService;
import util.ComplaintDisplayUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ComplaintMenuHelper {

    private static final ComplaintService complaintService =
            new ComplaintService();

    public static void complaintMenu(Scanner scanner) {

        boolean back = false;

        while (!back) {

            try {

                switch (ComplaintMenu.showComplaintMenu(scanner)) {

                    case 1:
                        registerComplaint(scanner);
                        break;

                    case 2:
                        ComplaintDisplayUtil.displayComplaints(
                                complaintService.viewAllComplaints()
                        );
                        break;


                    case 3:
                        searchComplaint(scanner);
                        break;

                    case 4:
                        updateComplaint(scanner);
                        break;

                    case 5:
                        deleteComplaint(scanner);
                        break;

                    case 6:
                        viewComplaintsByUser(scanner);
                        break;

                    case 7:
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


    private static void registerComplaint(Scanner scanner) {

        Complaint complaint = new Complaint();

        System.out.print("Enter Complaint ID (String) : ");
        complaint.setComplaintId(scanner.nextLine());

        System.out.print("Enter User ID (String) : ");
        complaint.setUserId(scanner.nextLine());

        System.out.print("""
                        Enter the category from the below :
                        
                        ROAD_DAMAGE("Road Damage")
                        WATER_LEAKAGE("Water Leakage"),
                        STREET_LIGHT("Street Light"),
                        GARBAGE("Garbage"),
                        DRAINAGE("Drainage"),
                        ELECTRICITY("Electricity"),
                        OTHER("Other");
                        
                        Enter the category :
                        """);
        complaint.setCategory(
                ComplaintCategory.valueOf(
                        scanner.nextLine().toUpperCase()
                )
        );

        System.out.print("Enter Description (String) : ");
        complaint.setDescription(scanner.nextLine());

        complaint.setComplaintDate(LocalDate.now());
        complaint.setStatus(ComplaintStatus.REGISTERED);
        complaint.setOfficerId(null);
        complaint.setResolution(null);
        complaint.setResolvedDate(null);

        if (complaintService.registerComplaint(complaint)) {
            System.out.println("\nComplaint registered successfully.\n");
        } else {
            System.out.println("\nComplaint registration failed.\n");
        }
    }

    private static void searchComplaint(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        Complaint complaint =
                complaintService.searchComplaintById(complaintId);

        if (complaint == null) {
            System.out.println("\nComplaint not found.\n");
            return;
        }

        ComplaintDisplayUtil.displayComplaints(
                List.of(complaint)
        );
    }

    private static void updateComplaint(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        Complaint complaint =
                complaintService.searchComplaintById(complaintId);

        if (complaint == null) {
            System.out.println("\nComplaint not found.\n");
            return;
        }

        System.out.print("Enter New Description (String) : ");
        complaint.setDescription(scanner.nextLine());

        if (complaintService.updateComplaint(complaint)) {
            System.out.println("\nComplaint updated successfully.\n");
        } else {
            System.out.println("\nComplaint update failed.\n");
        }
    }

    private static void deleteComplaint(Scanner scanner) {

        System.out.print("Enter Complaint ID (String) : ");
        String complaintId = scanner.nextLine();

        if (complaintService.deleteComplaint(complaintId)) {
            System.out.println("\nComplaint deleted successfully.\n");
        } else {
            System.out.println("\nComplaint deletion failed.\n");
        }
    }

    private static void viewComplaintsByUser(Scanner scanner) {

        System.out.print("Enter User ID (String) : ");
        String userId = scanner.nextLine();

        ComplaintDisplayUtil.displayComplaints(
                complaintService.viewComplaintsByUser(userId)
        );
    }




}
