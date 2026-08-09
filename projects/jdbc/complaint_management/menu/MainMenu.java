package menu;

import java.util.Scanner;

public class MainMenu {

    public static int showMainMenu(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("          COMPLAINT MANAGEMENT SYSTEM");
        System.out.println("==================================================");
        System.out.println("1. User Management");
        System.out.println("2. Officer Management");
        System.out.println("3. Complaint Management");
        System.out.println("4. Officer Assignment");
        System.out.println("5. Status Tracking");
        System.out.println("6. Resolution Management");
        System.out.println("7. Reports");
        System.out.println("8. Exit");
        System.out.println("==================================================");

        System.out.print("Enter your choice : ");
        return Integer.parseInt(scanner.nextLine());
    }

}
