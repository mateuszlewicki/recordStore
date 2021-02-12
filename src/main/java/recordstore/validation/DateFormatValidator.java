package recordstore.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import recordstore.entity.Release;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateFormatValidation implements ConstraintValidator<ValidDateFormat, String> {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);

    public DateFormatValidation() {
    }

    public DateFormatValidation(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            this.dateFormatter.parse(s);
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }
}