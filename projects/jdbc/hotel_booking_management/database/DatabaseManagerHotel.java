package database;

import constant.ApplicationConstants;
import enums.RoomStatus;
import enums.RoomType;

import java.sql.*;

public class DatabaseManagerHotel {

    DatabaseManagerHotel() {
    }

    public static void initialize() {
        createDatabase();
        createTables();
        checkAndInsertDefaultRooms();
    }

    private static void createDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS " + ApplicationConstants.DATABASE_NAME;
        try(Connection connection = DBConnection.getServerConnection();
            Statement statement = connection.createStatement()) {
            System.out.println("\nConnecting to MySQL server.");
            System.out.println("Connection established.\n");
            statement.executeUpdate(sql);
            System.out.println("Database Ready.\n");
        } catch(SQLException e) {
            System.out.println("Database creation failed.\n");
            System.out.println(e.getMessage());
        }
    }

    private static void createTables() {
        createRoomsTable();
        createCustomersTable();
        createBookingsTable();
    }

    private static void createRoomsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS rooms(
                room_id int PRIMARY KEY AUTO_INCREMENT,
                room_number varchar(10) UNIQUE NOT NULL,
                room_type varchar(20) NOT NULL,
                price_per_day DECIMAL(10,2) NOT NULL,
                status VARCHAR(20) NOT NULL
                )
                """;
        executeTableCreation(sql, "rooms");
    }

    private static void createCustomersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS customers(
                customer_id int PRIMARY KEY AUTO_INCREMENT,
                customer_name varchar(100) NOT NULL,
                phone varchar(10) UNIQUE NOT NULL,
                email varchar(100) UNIQUE NOT NULL,
                address varchar(100) NOT NULL,
                id_proof varchar(50) NOT NULL
                )
                """;
        executeTableCreation(sql, "customers");
    }

    private static void createBookingsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS bookings(
                booking_id int PRIMARY KEY AUTO_INCREMENT,
                customer_id int NOT NULL,
                room_id int NOT NULL,
                booking_date DATE NOT NULL,
                check_in_date DATE NOT NULL,
                check_out_date DATE NOT NULL,
                total_amount DECIMAL(10,2) DEFAULT 0,
                status varchar(20) NOT NULL,
                
                CONSTRAINT fk_customers FOREIGN KEY(customer_id) REFERENCES customers(customer_id),
                CONSTRAINT fk_rooms FOREIGN KEY(room_id) REFERENCES rooms(room_id)
                )
                """;
        executeTableCreation(sql, "bookings");
    }

    private static void executeTableCreation(String sql, String tableName) {
        try(Connection connection = DBConnection.getDatabaseConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println(tableName + " table is ready.\n");
        } catch (SQLException e) {
            System.out.println("Unable to create " + tableName + " table.\n");
            System.out.println(e.getMessage());
        }
    }

    private static void checkAndInsertDefaultRooms() {
        String sql = "SELECT COUNT(*) FROM rooms";

        try(Connection connection = DBConnection.getDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            if(resultSet.next()) {
                if(resultSet.getInt(1) == 0) {
                    insertDefaultRooms();
                    System.out.println("\nDefault Rooms Allocated.\n");
                } else {
                    System.out.println("\nDefault Rooms Already Available.\n");
                }
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void insertDefaultRooms() {
        String sql = """
                INSERT INTO rooms
                ( room_number, room_type, price_per_day, status )
                values (?, ?, ?, ?)
                """;

        try(Connection connection = DBConnection.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            addRoom(preparedStatement, "101", RoomType.SINGLE);
            addRoom(preparedStatement, "102", RoomType.SINGLE);
            addRoom(preparedStatement, "103", RoomType.SINGLE);

            addRoom(preparedStatement, "201", RoomType.DOUBLE);
            addRoom(preparedStatement, "202", RoomType.DOUBLE);
            addRoom(preparedStatement, "203", RoomType.DOUBLE);

            addRoom(preparedStatement, "301", RoomType.DELUXE);
            addRoom(preparedStatement, "302", RoomType.DELUXE);
            addRoom(preparedStatement, "303", RoomType.DELUXE);

            addRoom(preparedStatement, "401", RoomType.SUITE);
            addRoom(preparedStatement, "402", RoomType.SUITE);
            addRoom(preparedStatement, "403", RoomType.SUITE);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void addRoom(PreparedStatement preparedStatement,
                                String roomNumber,
                                RoomType roomType)
            throws SQLException {

        preparedStatement.setString(1, roomNumber);
        preparedStatement.setString(2, roomType.name());
        preparedStatement.setDouble(3, roomType.getDefaultPrice());
        preparedStatement.setString(4, RoomStatus.AVAILABLE.name());

        preparedStatement.addBatch();

        preparedStatement.executeBatch();

    }


}
