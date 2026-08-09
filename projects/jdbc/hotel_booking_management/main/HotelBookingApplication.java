package main;

import database.DatabaseInitializer;
import util.InputUtilHotel;

public class HotelBookingApplication {

    public static void main(String[] args) {

        DatabaseInitializer.initializeDatabase();

        System.out.println("\nDatabase initialization completed successfully.");

        while(true) {
            MenuHelper.showMainMenu();
            int choice = InputUtilHotel.getInt("Enter choice : ");

            switch (choice) {
                case 1 -> MenuHelper.roomMenu();
                case 2 -> MenuHelper.customerMenu();
                case 3 -> MenuHelper.bookingMenu();
                case 4 -> MenuHelper.checkInMenu();
                case 5 -> MenuHelper.checkOutMenu();
                case 6 -> MenuHelper.billMenu();
                case 7 -> MenuHelper.reportMenu();
                case 8 -> {
                    System.out.println("Thank You.");
                    InputUtilHotel.closeScanner();
                    System.exit(0);
                }
                default -> System.out.println("Invalid Choice...");
            }
        }

    }

}
