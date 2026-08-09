package main;

import model.Booking;
import model.Customer;
import model.Room;
import service.*;
import util.InputUtilHotel;

import java.time.LocalDate;
import java.util.List;

public class MenuHelper {

    private static final RoomService roomService = new RoomService();
    private static final CustomerService customerService = new CustomerService();
    private static final BookingService bookingService = new BookingService();
    private static final CheckInService checkInService = new CheckInService();
    private static final CheckOutService checkOutService = new CheckOutService();
    private static final BillingService billingService = new BillingService();
    private static final ReportService reportService = new ReportService();

    public static void showMainMenu() {
        System.out.println("\n=======================================");
        System.out.println("       HOTEL ROOM BOOKING SYSTEM");
        System.out.println("=======================================");
        System.out.println("""
                1. Room Management
                2. Customer Management
                3. Booking Management
                4. Check In Service
                5. Check Out Service
                6. Billing Management
                7. Report Management
                8. Exit
                """);
    }

    public static void roomMenu() {
        System.out.println("""
                1. View All Rooms
                2. View Available Rooms
                """);
        int choice = InputUtilHotel.getInt("Enter the choice : ");
        switch (choice) {
            case 1 -> {
                List<Room> rooms = roomService.getAllRooms();
                rooms.forEach(System.out::println);
            }
            case 2 -> {
                List<Room> rooms = roomService.getAvailableRooms();
                rooms.forEach(System.out::println);
            }
        }
    }

    public static void customerMenu() {
        System.out.println("""
                1. Register Customer
                2. View Customer
                """);
        int choice = InputUtilHotel.getInt("Enter the choice : ");
        switch (choice) {
            case 1 -> {
                Customer customer = new Customer();
                customer.setCustomerName(InputUtilHotel.getString("Enter the Name (String) : "));
                customer.setEmail(InputUtilHotel.getString("Enter the Email (String) : "));
                customer.setPhone(InputUtilHotel.getString("Enter the Phone Number (10-digits) : "));
                customer.setAddress(InputUtilHotel.getString("Enter the Address (String) : "));
                customer.setIdProof(InputUtilHotel.getString("Enter the ID Proof (String) : "));
                boolean result = customerService.registerCustomer(customer);
                System.out.println(result ? "\nCustomer Registered.\n" : "\nCustomer Registration Failed.\n");
            }
            case 2 -> {
                int id = InputUtilHotel.getInt("Enter the Customer ID (int) : ");
                Customer customer = customerService.getCustomerById(id);
                System.out.println("\n"+customer);
            }
        }
    }

    public static void bookingMenu() {
        int customerId = InputUtilHotel.getInt("Customer ID (int) : ");
        int roomId = InputUtilHotel.getInt("Room ID (int) : ");
        LocalDate checkIn = InputUtilHotel.getDate("Check In Date (yyyy-MM-dd) : ");
        LocalDate checkOut = InputUtilHotel.getDate("Check Out Date (yyyy-MM-dd) : ");
        boolean result = bookingService.createBooking(customerId, roomId, checkIn, checkOut);
        System.out.println(result ? "\nBooking Successful.\n" : "\nBooking Failed.\n");
    }

    public static void checkInMenu() {
        int bookingId = InputUtilHotel.getInt("Booking ID (int) : ");
        boolean result = checkInService.checkIn(bookingId);
        System.out.println(result ? "\nCheck-In Completed.\n" : "\nCheck-In Failed.\n");
    }

    public static void checkOutMenu() {
        int bookingId = InputUtilHotel.getInt("Booking ID (int) : ");
        boolean result = checkOutService.checkOut(bookingId);
        System.out.println(result ? "\nCheck-Out Completed.\n" : "\nCheck-Out Failed\n");
    }

    public static void billMenu() {
        int bookingId = InputUtilHotel.getInt("Booking ID (int) : ");
        Booking booking = bookingService.getBookingById(bookingId);
        billingService.printBill(booking);
    }

    public static void reportMenu() {
        System.out.println("""
                1. Available Rooms
                2. All Bookings
                """);
        int choice = InputUtilHotel.getInt("Enter the choice : ");
        switch (choice) {
            case 1 -> reportService.showAvailableRooms();
            case 2 -> reportService.showAllBookings();
        }
    }


}
