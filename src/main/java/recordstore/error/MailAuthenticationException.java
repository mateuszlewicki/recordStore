package recordstore.error;

public class MailAuthenticationException extends RuntimeException {

    public MailAuthenticationException(String message) {
        super(message);
    }

    public MailAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailAuthenticationException(Throwable cause) {
        super(cause);
    }

    public MailAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
