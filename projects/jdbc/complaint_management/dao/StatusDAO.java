package dao;

import enums.ComplaintStatus;
import model.Complaint;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface StatusDAO {

    boolean updateStatus(Connection connection,
                         String complaintId,
                         ComplaintStatus status) throws SQLException;

    ComplaintStatus viewStatus(Connection connection,
                               String complaintId) throws SQLException;

    List<Complaint> viewComplaintsByStatus(Connection connection,
                                           ComplaintStatus status) throws SQLException;

    List<Complaint> viewPendingComplaints(Connection connection) throws SQLException;

    List<Complaint> viewInProgressComplaints(Connection connection) throws SQLException;

    List<Complaint> viewResolvedComplaints(Connection connection) throws SQLException;

    List<Complaint> viewClosedComplaints(Connection connection) throws SQLException;

}
