package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import recordstore.enums.Format;
import recordstore.validation.MultipartFileSize;
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

    private String img;

    @MultipartFileSize
    private MultipartFile data;
}