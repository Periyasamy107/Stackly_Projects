package exception;

public class AccessDeniedException extends VotingException {
    public AccessDeniedException(String message) {
        super(message);
    }
}