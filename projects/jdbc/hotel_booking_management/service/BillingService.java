package service;

import model.Booking;

import java.time.temporal.ChronoUnit;

public class BillingService {

    public double calculateBill(Booking booking) {
        long days = ChronoUnit.DAYS.between(
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );

        return days * booking.getRoom().getPricePerDay();
    }

    public void printBill(Booking booking) {
        double amount = calculateBill(booking);
        System.out.println("============= BILL ==============");
        System.out.println("Customer Name : " + booking.getCustomer().getCustomerName());
        System.out.println("Room Number   : " + booking.getRoom().getRoomNumber());
        System.out.println("Total Amount  : " + amount);
        System.out.println("=================================");
    }

}
