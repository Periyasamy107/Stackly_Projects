package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtilHotel {

    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static int getInt(String message) {
        while(true) {
            try{
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number only.\n");
            }
        }
    }

    public static double getDouble(String message) {
        while(true) {
            try{
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid decimal value.\n");
            }
        }
    }

    public static LocalDate getDate(String message) {
        while(true) {
            try{
                System.out.print(message);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format. Use yyyy-MM-dd format.");
            }
        }
    }

    public static boolean getBoolean(String message) {
        while(true) {
            String input = getString(message);
            if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                return true;
            }
            if(input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Please enter 'yes' or 'no'");
        }
    }

    public static void closeScanner() {
        scanner.close();
    }

}
