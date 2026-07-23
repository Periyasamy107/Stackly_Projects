package exception;

public class StudentNotEligibleException extends VotingException {
    public StudentNotEligibleException(String message) {
        super(message);
    }
}
