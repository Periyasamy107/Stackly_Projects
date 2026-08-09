package menu;

import java.util.Scanner;

public class ResolutionMenu {

    public static int showResolutionMenu(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("             RESOLUTION MANAGEMENT");
        System.out.println("==================================================");
        System.out.println("1. Add Resolution");
        System.out.println("2. View Resolution");
        System.out.println("3. Mark Complaint As Resolved");
        System.out.println("4. View Resolved Complaint");
        System.out.println("5. Back");
        System.out.println("==================================================");

        System.out.print("Enter your choice : ");
        return Integer.parseInt(scanner.nextLine());
    }
}
