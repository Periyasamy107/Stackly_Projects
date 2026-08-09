package exception;

public class OfficerNotFoundException extends RuntimeException {
    public OfficerNotFoundException(String message) {
        super(message);
    }
}
