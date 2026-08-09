package menu;

import java.util.Scanner;

public class ReportMenu {

    public static int showReportMenu(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("                  REPORTS");
        System.out.println("==================================================");
        System.out.println("1. Complaint Count By Status");
        System.out.println("2. Complaint Count By Category");
        System.out.println("3. Complaint Count By Officer");
        System.out.println("4. View Unresolved Complaints");
        System.out.println("5. View Resolved Complaints");
        System.out.println("6. Back");
        System.out.println("==================================================");

        System.out.print("Enter your choice : ");
        return Integer.parseInt(scanner.nextLine());
    }

}
