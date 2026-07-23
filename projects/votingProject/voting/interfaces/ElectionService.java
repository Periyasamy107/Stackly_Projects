package interfaces;

import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import model.Election;

import java.util.List;

public interface ElectionService {

    boolean createElection(Election election) throws UserAlreadyExistsException, InputValidationException;

    boolean updateElection(Election election) throws UserNotFoundException, InputValidationException;

    boolean deleteElection(String electionId) throws UserNotFoundException;

    boolean startElection(String electionId) throws UserNotFoundException;

    boolean closeElection(String electionId) throws UserNotFoundException;

    Election searchElection(String electionId) throws UserNotFoundException;

    List<Election> getallElections();

}
