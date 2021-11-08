package recordstore.validation;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateFormatValidatorTest {

    @Test
    void givenValidator_whenValidDatePassed_ThenTrue(){
        ConstraintValidatorContext context = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        DateFormatValidator validator = new DateFormatValidator(dateFormatter);

        assertTrue(validator.isValid("2019-02-28", context));
    }

    @Test
    void givenValidator_whenValidDatePassed_ThenFalse(){
        ConstraintValidatorContext context = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        DateFormatValidator validator = new DateFormatValidator(dateFormatter);

        assertFalse(validator.isValid("2019-02-30", context));
    }
}