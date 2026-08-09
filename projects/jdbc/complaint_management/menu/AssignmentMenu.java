package menu;

import java.util.Scanner;

public class AssignmentMenu {

    public static int showAssignmentMenu(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("             OFFICER ASSIGNMENT");
        System.out.println("==================================================");
        System.out.println("1. Assign Officer");
        System.out.println("2. Reassign Officer");
        System.out.println("3. View Assigned Complaints");
        System.out.println("4. View Unassigned Complaints");
        System.out.println("5. Back");
        System.out.println("==================================================");

        System.out.print("Enter your choice : ");
        return Integer.parseInt(scanner.nextLine());
    }
}
