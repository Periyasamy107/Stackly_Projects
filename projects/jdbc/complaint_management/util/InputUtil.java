package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String message) {
        while(true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if(!value.isEmpty()) {
                return value;
            }
            System.out.println("\nInput Cannot Be Empty.\n");
        }
    }

    public static int getInt(String message) {
        while(true) {
            try{
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid Integer Number.\n");
            }
        }
    }

    public static long getLong(String message) {
        while(true) {
            try{
                System.out.print(message);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid Long Number.\n");
            }
        }
    }

    public static double getDouble(String message) {
        while (true) {
            try{
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid Decimal Number.\n");
            }
        }
    }

    public static LocalDate getDate(String message) {
        while (true) {
            try{
                System.out.print(message + " (yyyy-MM-dd) : ");
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid Date Format.\n");
            }
        }
    }

    public static boolean getBoolean(String message) {
        while (true) {
            System.out.print(message + " (Y/N) : ");
            String value = scanner.nextLine().trim();
            if(value.equalsIgnoreCase("Y")){
                return true;
            }
            if(value.equalsIgnoreCase("N")){
                return false;
            }
            System.out.println("\nPlease Enter 'Y' or 'N'.");
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void closeScanner() {
        scanner.close();
    }

}
