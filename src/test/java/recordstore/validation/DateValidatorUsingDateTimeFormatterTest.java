package recordstore.validation;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DateValidatorUsingDateTimeFormatterTest {

    @Test
    void testDateValidator(){
        ConstraintValidatorContext context = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.GERMANY)
                .withResolverStyle(ResolverStyle.STRICT);
        DateValidatorUsingDateTimeFormatter validator = new DateValidatorUsingDateTimeFormatter(dateFormatter);

        assertTrue(validator.isValid("2019-02-28", context));
        assertFalse(validator.isValid("2019-02-30", context));
    }
}