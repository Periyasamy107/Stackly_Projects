package dao;

import database.DBConnectionHotel;
import enums.RoomStatus;
import enums.RoomType;
import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public boolean save(Room room) {
        String sql = """
                INSERT INTO rooms
                (room_number, room_type, price_per_day, status)
                values (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnectionHotel.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getRoomType().name());
            preparedStatement.setDouble(3, room.getPricePerDay());
            preparedStatement.setString(4, room.getRoomStatus().name());

            int rows = preparedStatement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public Room findById(int roomId) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";

        try (Connection connection = DBConnectionHotel.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roomId);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return mapRoom(result);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection connection = DBConnectionHotel.getDatabaseConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            System.out.println();
            while(result.next()) {
                rooms.add(mapRoom(result));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rooms;
    }


    public List<Room> findAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status = ?";

        try (Connection connection = DBConnectionHotel.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, RoomStatus.AVAILABLE.name());

            ResultSet result = statement.executeQuery();

            System.out.println();
            while(result.next()) {
                rooms.add(mapRoom(result));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rooms;
    }


    public boolean updateStatus(int roomId, RoomStatus status) {
        String sql = "UPDATE rooms SET status = ? WHERE room_id = ?";

        try (Connection connection = DBConnectionHotel.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status.name());
            statement.setInt(2, roomId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean removeRoom(int roomId) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection connection = DBConnectionHotel.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roomId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    private Room mapRoom(ResultSet result) throws SQLException {
        Room room = new Room();

        room.setRoomId(result.getInt("room_id"));
        room.setRoomNumber(result.getString("room_number"));
        room.setRoomType(RoomType.valueOf(result.getString("room_type")));
        room.setPricePerDay(result.getDouble("price_per_day"));
        room.setRoomStatus(RoomStatus.valueOf(result.getString("status")));

        return room;

    }

}
