package recordstore.error;

public class AccountAlreadyExistException extends RuntimeException {

    public AccountAlreadyExistException() {
        super();
    }

    public AccountAlreadyExistException(String message) {
        super(message);
    }

    public AccountAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountAlreadyExistException(Throwable cause) {
        super(cause);
    }
}