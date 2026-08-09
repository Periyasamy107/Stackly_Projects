package menu;

import java.util.Scanner;

public class UserMenu {

    public static int showUserMenu(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("               USER MANAGEMENT");
        System.out.println("==================================================");
        System.out.println("1. Register User");
        System.out.println("2. View All Users");
        System.out.println("3. Search User");
        System.out.println("4. Update User");
        System.out.println("5. Deactivate User");
        System.out.println("6. View Active Users");
        System.out.println("7. View Deactivated Users");
        System.out.println("8. Back");
        System.out.println("==================================================");

        System.out.print("Enter your choice : ");
        return Integer.parseInt(scanner.nextLine());
    }

}
