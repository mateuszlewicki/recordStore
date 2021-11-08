package recordstore.error;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(final EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Object> mailAuthenticationHandler() {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error in mail configuration", "MailError");
        return ResponseEntity.status(409).body(errors);
    }

    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExist(final AccountAlreadyExistException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> tokenExpiredHandler(TokenExpiredException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("The file is too large to upload!", "FileSizeError");
        return ResponseEntity.status(409).body(errors);
    }

    @ExceptionHandler(AccountAlreadyActivatedException.class)
    public ResponseEntity<Object> accountAlreadyActivatedHandler(AccountAlreadyActivatedException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    // add new exception for this case
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException ex) {
        return ResponseEntity.status(403).body(ex.getMessage());
    }
}