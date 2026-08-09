package service;

import dao.BookingDAO;
import enums.BookingStatus;
import exception.InvalidBookingException;
import model.Booking;

public class CheckInService {

    private final BookingDAO bookingDAO;
    private final BookingService bookingService;

    public CheckInService() {
        bookingDAO = new BookingDAO();
        bookingService = new BookingService();
    }

    public boolean checkIn(int bookingId) {
        Booking booking = bookingDAO.findById(bookingId);
        if(booking.getBookingStatus() != BookingStatus.BOOKED) {
            throw new InvalidBookingException("Customer cannot check-in.");
        }
        return bookingDAO.updateBookingStatus(bookingId, BookingStatus.CHECKED_IN);
    }

}
