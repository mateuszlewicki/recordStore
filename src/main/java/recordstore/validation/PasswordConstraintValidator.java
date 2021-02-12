package recordstore.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        return password.length() > 6 && password.length() < 20;
    }
}
