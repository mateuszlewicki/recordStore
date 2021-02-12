package recordstore.validation;

import recordstore.DTO.AccountDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        AccountDTO accountDTO = (AccountDTO) o;
        return accountDTO.getPassword().equals(accountDTO.getPasswordConfirm());
    }
}