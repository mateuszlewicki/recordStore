package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Release;
import recordstore.validation.MultipartFileSize;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ArtistDTO {

    private long id;

    @NotBlank(message = "Field is mandatory")
    private String name;

    private String country;

    private String description;

    private String img = "noImageAvailable.png";

    @MultipartFileSize
    private MultipartFile data;

    private Set<Release> releases = new HashSet<>();
}