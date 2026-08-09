package service;

import dao.BookingDAO;
import dao.RoomDAO;
import enums.BookingStatus;
import enums.RoomStatus;
import exception.InvalidBookingException;
import model.Booking;

public class CheckOutService {

    private final BookingDAO bookingDAO;
    private final RoomDAO roomDAO;
    private final BookingService bookingService;

    public CheckOutService() {
        bookingDAO = new BookingDAO();
        roomDAO = new RoomDAO();
        bookingService = new BookingService();
    }

    public boolean checkOut(int bookingId) {

        Booking booking = bookingDAO.findById(bookingId);

        if(booking.getBookingStatus() != BookingStatus.CHECKED_IN) {
            throw new InvalidBookingException("Customer is not checked-in.");
        }

        boolean bookingUpdated = bookingDAO.updateBookingStatus(bookingId, BookingStatus.CHECKED_OUT);
        boolean roomUpdated = roomDAO.updateStatus(booking.getRoom().getRoomId(), RoomStatus.AVAILABLE);

        return bookingUpdated && roomUpdated;
    }

}
