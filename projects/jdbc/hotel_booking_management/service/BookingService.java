package service;

import dao.BookingDAO;
import enums.BookingStatus;
import enums.RoomStatus;
import exception.InvalidBookingException;
import model.Booking;
import model.Customer;
import model.Room;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookingService {

    private final BookingDAO bookingDAO;
    private final RoomService roomService;
    private final CustomerService customerService;

    public BookingService() {
        bookingDAO = new BookingDAO();
        roomService = new RoomService();
        customerService = new CustomerService();
    }

    public boolean createBooking(int customerId, int roomId, LocalDate checkIn, LocalDate checkOut) {
        Customer customer = customerService.getCustomerById(customerId);
        Room room = roomService.getRoomById(roomId);

        if(room.getRoomStatus() != RoomStatus.AVAILABLE) {
            throw new InvalidBookingException("Room is not available.");
        }

        if(checkOut.isBefore(checkIn) || checkOut.equals(checkIn)) {
            throw new InvalidBookingException("Invalid check-out date.");
        }

        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        double amount = days * room.getPricePerDay();

        Booking booking = new Booking();

        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setBookingDate(LocalDate.now());
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        booking.setTotalAmount(amount);
        booking.setBookingStatus(BookingStatus.BOOKED);

        return bookingDAO.save(booking);
    }

    public Booking getBookingById(int bookingId) {
        Booking booking = bookingDAO.findById(bookingId);
        if(booking == null) {
            throw new InvalidBookingException("Booking Not Found.");
        }
        return booking;
    }

    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }

    public boolean cancelBooking(int bookingId) {
        return bookingDAO.updateBookingStatus(bookingId, BookingStatus.CANCELLED);
    }

}
