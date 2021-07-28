package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class TrackFormDTO {

    private long id;

    @NotBlank(message = "Field is mandatory")
    String position;

    @NotBlank(message = "Field is mandatory")
    String title;
}