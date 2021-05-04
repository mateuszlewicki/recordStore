package recordstore.error;

public class WrongIdException extends RuntimeException {

    public WrongIdException() {
    }

    public WrongIdException(String message) {
        super(message);
    }

    public WrongIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongIdException(Throwable cause) {
        super(cause);
    }

    public WrongIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
