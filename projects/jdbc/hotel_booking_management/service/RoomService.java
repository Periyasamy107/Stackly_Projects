package service;

import dao.RoomDAO;
import enums.RoomStatus;
import exception.RoomNotFoundException;
import model.Room;

import java.util.List;

public class RoomService {

    private final RoomDAO roomDAO;

    public RoomService() {
        this.roomDAO = new RoomDAO();
    }

    public boolean addRoom(Room room) {
        return roomDAO.save(room);
    }

    public List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    public List<Room> getAvailableRooms() {
        return roomDAO.findAvailableRooms();
    }

    public Room getRoomById(int roomId) {
        Room room = roomDAO.findById(roomId);
        if(room == null) {
            throw new RoomNotFoundException("Room ID " + roomId + " is not found");
        }
        return room;
    }

    public boolean updateRoomStatus(int roomId, RoomStatus roomStatus) {
        getRoomById(roomId);
        return roomDAO.updateStatus(roomId, roomStatus);
    }

    public boolean removeRoom(int roomId) {
        getRoomById(roomId);
        return roomDAO.removeRoom(roomId);
    }
}
