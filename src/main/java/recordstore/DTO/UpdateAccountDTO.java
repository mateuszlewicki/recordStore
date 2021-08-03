package recordstore.DTO;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateAccountDTO {

    @NotBlank(message = "Field is mandatory")
    private String username;
}