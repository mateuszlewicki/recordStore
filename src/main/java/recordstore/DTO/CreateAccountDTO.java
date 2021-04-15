package recordstore.DTO;

import lombok.Getter;
import lombok.Setter;
import recordstore.validation.PasswordMatches;
import recordstore.validation.ValidEmail;
import recordstore.validation.ValidPassword;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@PasswordMatches
public class CreateAccountDTO {

    @NotBlank(message = "Field is mandatory")
    private String username;

    @NotBlank(message = "Field is mandatory")
    @ValidPassword
    private String password;

    @NotBlank(message = "Field is mandatory")
    private String passwordConfirm;

    @NotBlank(message = "Field is mandatory")
    @ValidEmail
    private String email;

    private String img = "noImageAvailable.png";
}