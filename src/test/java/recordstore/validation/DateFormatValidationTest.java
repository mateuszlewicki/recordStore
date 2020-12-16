package recordstore.validation;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class DateFormatValidationTest {

    @Test
    void givenValidator_whenValidDatePassed_ThenTrue(){
        ConstraintValidatorContext context = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        DateFormatValidation validator = new DateFormatValidation(dateFormatter);

        assertTrue(validator.isValid("2019-02-28", context));
    }

    @Test
    void givenValidator_whenValidDatePassed_ThenFalse(){
        ConstraintValidatorContext context = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        DateFormatValidation validator = new DateFormatValidation(dateFormatter);

        assertFalse(validator.isValid("2019-02-30", context));
    }
}