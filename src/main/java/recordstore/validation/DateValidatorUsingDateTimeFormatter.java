package recordstore.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidatorUsingDateTimeFormatter implements ConstraintValidator<ValidDateFormat, String> {

    private DateTimeFormatter dateFormatter;

    public DateValidatorUsingDateTimeFormatter(DateTimeFormatter dateFormatter){
        this.dateFormatter = dateFormatter;
    }

    @Override
    public void initialize(ValidDateFormat constraintAnnotation) {
    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext context) {
        try {
            this.dateFormatter.parse(dateStr);
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }
}
