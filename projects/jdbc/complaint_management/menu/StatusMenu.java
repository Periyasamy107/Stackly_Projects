package menu;

import java.util.Scanner;

public class StatusMenu {

    public static int showStatusMenu(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("               STATUS TRACKING");
        System.out.println("==================================================");
        System.out.println("1. Update Complaint Status");
        System.out.println("2. View Complaint Status");
        System.out.println("3. View Registered Complaints");
        System.out.println("4. View Assigned Complaints");
        System.out.println("5. View In-Progress Complaints");
        System.out.println("6. View On-Hold Complaints");
        System.out.println("7. View Resolved Complaints");
        System.out.println("8. View Closed Complaints");
        System.out.println("9. Back");
        System.out.println("==================================================");

        System.out.print("Enter your choice : ");
        return Integer.parseInt(scanner.nextLine());
    }
}
