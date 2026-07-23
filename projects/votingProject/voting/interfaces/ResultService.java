package interfaces;

import exception.UserNotFoundException;
import model.Result;

import java.util.List;

public interface ResultService {

    List<Result> generateResult(String electionId) throws UserNotFoundException;

    Result getWinner(String electionId) throws UserNotFoundException;

}
