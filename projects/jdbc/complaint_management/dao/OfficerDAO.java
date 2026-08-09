package dao;

import model.Officer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OfficerDAO {

    boolean registerOfficer(Connection connection, Officer officer) throws SQLException;

    List<Officer> viewAllOfficers(Connection connection) throws SQLException;

    Officer searchOfficerById(Connection connection, String officerId) throws SQLException;

    boolean updateOfficer(Connection connection, Officer officer) throws SQLException;

    boolean deactivateOfficer(Connection connection, String officerId) throws SQLException;

    List<Officer> viewActiveOfficers(Connection connection) throws SQLException;

}
