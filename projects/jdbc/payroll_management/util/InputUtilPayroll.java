package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtilPayroll {

    private static final Scanner scanner = new Scanner(System.in);

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static String readString(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Please try again.");
        }
    }


    public static String readOptionalString(String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }


    public static int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid integer.");
            }
        }
    }


    public static int readPositiveInt(String message) {

        while (true) {

            int value = readInt(message);

            if (value > 0) {
                return value;
            }

            System.out.println("Value must be greater than zero.");
        }
    }


    public static double readDouble(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {

                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }


    public static double readNonNegativeDouble(String message) {

        while (true) {

            double value = readDouble(message);

            if (value >= 0) {
                return value;
            }

            System.out.println("Value cannot be negative.");
        }
    }


    public static LocalDate readDate(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {

                return LocalDate.parse(input, DATE_FORMATTER);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date. Please use format yyyy-MM-dd."
                );
            }
        }
    }


    public static LocalDate readOptionalDate(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {

                return LocalDate.parse(input, DATE_FORMATTER);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date. Please use format yyyy-MM-dd."
                );
            }
        }
    }


    public static boolean readYesNo(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Y")) {
                return true;
            }

            if (input.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Please enter Y or N.");
        }
    }


    public static String readEmail(String message) {

        while (true) {

            String email = readString(message);

            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return email;
            }

            System.out.println("Please enter a valid email address.");
        }
    }


    public static String readPhone(String message) {

        while (true) {

            String phone = readString(message);

            if (phone.matches("\\d{10}")) {
                return phone;
            }

            System.out.println(
                    "Phone number must contain exactly 10 digits."
            );
        }
    }


    public static String readStatus(String message) {

        while (true) {

            String status = readString(message).toUpperCase();

            if (status.equals("ACTIVE") ||
                    status.equals("INACTIVE")) {

                return status;
            }

            System.out.println(
                    "Status must be ACTIVE or INACTIVE."
            );
        }
    }


    public static String readPaymentStatus(String message) {

        while (true) {

            String status = readString(message).toUpperCase();

            if (status.equals("PENDING") ||
                    status.equals("PROCESSED")) {

                return status;
            }

            System.out.println(
                    "Payment status must be PENDING or PROCESSED."
            );
        }
    }


    public static Scanner getScanner() {

        return scanner;
    }
}