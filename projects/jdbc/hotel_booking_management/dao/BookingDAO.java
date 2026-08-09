package dao;

import database.DBConnection;
import enums.BookingStatus;
import enums.RoomStatus;
import enums.RoomType;
import model.Booking;
import model.Customer;
import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean save(Booking booking) {
        String bookingSql = """
                INSERT INTO bookings
                (customer_id, room_id, booking_date, check_in_date, check_out_date, total_amount, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        String roomSql = "UPDATE rooms SET status = ? WHERE room_id = ?";

        Connection connection = null;

        try {
            connection = DBConnection.getDatabaseConnection();
            connection.setAutoCommit(false);

            PreparedStatement bookingSt =  connection.prepareStatement(
                    bookingSql, Statement.RETURN_GENERATED_KEYS
            );

            bookingSt.setInt(1, booking.getCustomer().getCustomerId());
            bookingSt.setInt(2, booking.getRoom().getRoomId());
            bookingSt.setDate(3, Date.valueOf(booking.getBookingDate()));
            bookingSt.setDate(4, Date.valueOf(booking.getCheckInDate()));
            bookingSt.setDate(5, Date.valueOf(booking.getCheckOutDate()));
            bookingSt.setDouble(6, booking.getTotalAmount());
            bookingSt.setString(7, booking.getBookingStatus().name());

            int bookingResult = bookingSt.executeUpdate();

            if(bookingResult == 0) {
                connection.rollback();
                return false;
            }

            ResultSet keys = bookingSt.getGeneratedKeys();

            if(keys.next()) {
                booking.setBookingId(keys.getInt(1));
            }

            PreparedStatement roomSt = connection.prepareStatement(roomSql);

            roomSt.setString(1, RoomStatus.BOOKED.name());
            roomSt.setInt(2, booking.getRoom().getRoomId());

            int roomResult = roomSt.executeUpdate();

            if(roomResult == 0) {
                connection.rollback();
                return false;
            }

            connection.commit();

            return true;

        } catch (SQLException e) {
            try{
                if(connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollBackException) {
                System.out.println(rollBackException.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                if(connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return false;
    }


    public Booking findById(int bookingId) {
        String sql = """
                SELECT
                    b.booking_id, b.booking_date, b.check_in_date, b.check_out_date, b.total_amount, b.status AS booking_status,
                    c.customer_id, c.customer_name, c.email, c.phone, c.address, c.id_proof,
                    r.room_id, r.room_number, r.room_type, r.price_per_day, r.status AS room_status
                FROM bookings b
                JOIN customers c on c.customer_id = b.customer_id
                JOIN rooms r on r.room_id = b.room_id
                WHERE b.booking_id = ?
                """;

        try (Connection connection = DBConnection.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookingId);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return mapBooking(result);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  null;
    }


    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();

        String sql = """
                SELECT
                    b.booking_id, b.booking_date, b.check_in_date, b.check_out_date, b.total_amount, b.status booking_status,
                    c.customer_id, c.customer_name, c.email, c.phone, c.address, c.id_proof,
                    r.room_id, r.room_number, r.room_type, r.price_per_day, r.status room_status
                FROM bookings b
                JOIN customers c on c.customer_id = b.customer_id
                JOIN rooms r on r.room_id = b.room_id
                """;

        try (Connection connection = DBConnection.getDatabaseConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while(result.next()) {
                bookings.add(mapBooking(result));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookings;
    }


    public boolean updateBookingStatus(int bookingId, BookingStatus bookingStatus) {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";

        try (Connection connection = DBConnection.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bookingStatus.name());
            statement.setInt(2, bookingId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    private Booking mapBooking(ResultSet result) throws SQLException {

        Customer customer = new Customer();

        customer.setCustomerId(result.getInt("customer_id"));
        customer.setCustomerName(result.getString("customer_name"));
        customer.setEmail(result.getString("email"));
        customer.setPhone(result.getString("phone"));
        customer.setAddress(result.getString("address"));
        customer.setIdProof(result.getString("id_proof"));

        Room room = new Room();

        room.setRoomId(result.getInt("room_id"));
        room.setRoomNumber(result.getString("room_number"));
        room.setRoomType(RoomType.valueOf(result.getString("room_type")));
        room.setPricePerDay(result.getDouble("price_per_day"));
        room.setRoomStatus(RoomStatus.valueOf(result.getString("room_status")));


        Booking booking = new Booking();

        booking.setBookingId(result.getInt("booking_id"));
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setBookingDate(result.getDate("booking_date").toLocalDate());
        booking.setCheckInDate(result.getDate("check_in_date").toLocalDate());
        booking.setCheckOutDate(result.getDate("check_out_date").toLocalDate());
        booking.setTotalAmount(result.getDouble("total_amount"));
        booking.setBookingStatus(BookingStatus.valueOf(result.getString("booking_status")));

        return booking;

    }

}
