package service;

import model.Booking;
import model.Room;

import java.util.List;

public class ReportService {

    private final RoomService roomService;
    private final BookingService bookingService;

    public ReportService() {
        roomService = new RoomService();
        bookingService = new BookingService();
    }

    public void showAvailableRooms() {
        List<Room> rooms = roomService.getAvailableRooms();
        for(Room room : rooms) {
            System.out.println(room);
        }
    }

    public void showAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        for(Booking booking : bookings) {
            System.out.println(booking);
        }
    }

}
