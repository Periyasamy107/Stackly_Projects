package interfaces;

import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import model.Candidate;

import java.util.List;

public interface CandidateService {

    boolean addCandidate(Candidate candidate) throws UserAlreadyExistsException, InputValidationException;

    boolean updateCandidate(Candidate candidate) throws UserNotFoundException, InputValidationException;

    boolean deleteCandidate(String candidateId) throws UserNotFoundException;

    Candidate searchCandidate(String candidateId) throws UserNotFoundException;

    List<Candidate> getAllCandidates();

}
