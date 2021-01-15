package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.*;
import recordstore.enums.Format;
import recordstore.validation.ValidDateFormat;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ReleaseDTO {

    private long id;

    @NotBlank(message = "Field is mandatory")
    private String code;

    @NotBlank(message = "Field is mandatory")
    private String title;

    @ValidDateFormat
    private String releaseDate;

    private Format format;
    private String img = "noImageAvailable.png";
    private MultipartFile data;
    private Set<Artist> artists = new HashSet<>();
    private Set<Genre> genres = new HashSet<>();
    private Label label;
    private List<Track> tracklist = new ArrayList<>();
    private List<YouTubeVideo> playlist = new ArrayList<>();
}
