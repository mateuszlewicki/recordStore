package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import recordstore.enums.Format;
import recordstore.validation.ValidDateFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class ReleaseFormDTO {

    private long id;

    @NotBlank(message = "Field is mandatory")
    private String code;

    @NotBlank(message = "Field is mandatory")
    private String title;

    @ValidDateFormat
    private String releaseDate;

    @NotNull(message = "Field is mandatory")
    private Format format;
}