package dao;

import model.Complaint;

import java.sql.Connection;
import java.sql.SQLException;

public interface ResolutionDAO {

    boolean addResolution(Connection connection,
                          String complaintId,
                          String resolution) throws SQLException;

    String viewResolution(Connection connection,
                          String complaintId) throws SQLException;

    boolean markAsResolved(Connection connection,
                           String complaintId,
                           String resolution) throws SQLException;

    Complaint viewResolvedComplaint(Connection connection,
                                    String complaintId) throws SQLException;


}
