package dao;

import model.Complaint;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ComplaintReportDAO {

    Map<String, Long> getComplaintCountByStatus(
            Connection connection) throws SQLException;

    Map<String, Long> getComplaintCountByCategory(
            Connection connection) throws SQLException;

    Map<String, Long> getComplaintCountByOfficer(
            Connection connection) throws SQLException;

    List<Complaint> getUnresolvedComplaints(
            Connection connection) throws SQLException;

    List<Complaint> getResolvedComplaints(
            Connection connection) throws SQLException;

}
