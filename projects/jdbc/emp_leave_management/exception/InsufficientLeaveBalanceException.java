package exception;

public class InsufficientLeaveBalanceException extends  Exception{

    public InsufficientLeaveBalanceException(String message) {
        super(message);
    }

}
