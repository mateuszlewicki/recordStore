package recordstore.error;

public class AccountAlreadyActivatedException extends RuntimeException {

    public AccountAlreadyActivatedException() {
    }

    public AccountAlreadyActivatedException(String message) {
        super(message);
    }

    public AccountAlreadyActivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountAlreadyActivatedException(Throwable cause) {
        super(cause);
    }

    public AccountAlreadyActivatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}