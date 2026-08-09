package dao;

import model.Complaint;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ComplaintDAO {

    boolean registerComplaint(Connection connection, Complaint complaint) throws SQLException;

    List<Complaint> viewAllComplaints(Connection connection) throws SQLException;

    Complaint searchComplaintById(Connection connection, String complaintId) throws SQLException;

    boolean updateComplaint(Connection connection, Complaint complaint) throws SQLException;

    boolean deleteComplaint(Connection connection, String complaintId) throws SQLException;

    List<Complaint> viewComplaintsByUser(Connection connection, String userId) throws SQLException;

}
