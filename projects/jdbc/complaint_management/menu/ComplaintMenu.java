package menu;

import java.util.Scanner;

public class ComplaintMenu {

    public static int showComplaintMenu(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("            COMPLAINT MANAGEMENT");
        System.out.println("==================================================");
        System.out.println("1. Register Complaint");
        System.out.println("2. View All Complaints");
        System.out.println("3. Search Complaint");
        System.out.println("4. Update Complaint");
        System.out.println("5. Delete Complaint");
        System.out.println("6. View Complaints By User");
        System.out.println("7. Back");
        System.out.println("==================================================");

        System.out.print("Enter your choice : ");
        return Integer.parseInt(scanner.nextLine());
    }

}
