package dao;

import model.Complaint;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AssignmentDAO {

    boolean assignOfficer(Connection connection,
                          String complaintId,
                          String officerId) throws SQLException;

    boolean reassignOfficer(Connection connection,
                            String complaintId,
                            String officerId) throws SQLException;

    List<Complaint> viewAssignedComplaints(Connection connection) throws SQLException;

    List<Complaint> viewUnassignedComplaints(Connection connection) throws SQLException;

}
