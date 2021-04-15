package recordstore.validation;

import recordstore.DTO.CreateAccountDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        CreateAccountDTO accountDTO = (CreateAccountDTO) o;
        return accountDTO.getPassword().equals(accountDTO.getPasswordConfirm());
    }
}