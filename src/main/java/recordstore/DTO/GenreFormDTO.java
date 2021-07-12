package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class GenreFormDTO {

    private long id;

    @NotBlank(message = "Field is mandatory")
    private String title;
}