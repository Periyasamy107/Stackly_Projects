package interfaces;

import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import model.Vote;

import java.util.List;

public interface VotingService {

    boolean castVote(Vote vote)
        throws UserAlreadyExistsException,
            UserNotFoundException,
            InputValidationException;

    List<Vote> getAllVotes();

    boolean checkStudentVoted(String studentId, String electionId);

}
