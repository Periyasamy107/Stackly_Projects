package menu;


import java.util.Scanner;

public class OfficerMenu {

    public static int showOfficerMenu(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("             OFFICER MANAGEMENT");
        System.out.println("==================================================");
        System.out.println("1. Register Officer");
        System.out.println("2. View All Officers");
        System.out.println("3. Search Officer");
        System.out.println("4. Update Officer");
        System.out.println("5. Deactivate Officer");
        System.out.println("6. View Active Officers");
        System.out.println("7. Back");
        System.out.println("==================================================");

        System.out.print("Enter your choice : ");
        return Integer.parseInt(scanner.nextLine());
    }

}
