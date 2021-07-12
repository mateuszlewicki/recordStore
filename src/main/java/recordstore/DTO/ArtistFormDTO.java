package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import recordstore.validation.MultipartFileSize;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class ArtistFormDTO {

    private long id;

    @NotBlank(message = "Field is mandatory")
    private String name;

    private String country;

    @Size(max = 2000, message = "Description too long, max size 2000")
    private String description;

    private String img;

    @MultipartFileSize
    private MultipartFile data;
}