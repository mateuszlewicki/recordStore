package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.*;
import recordstore.enums.Format;
import recordstore.validation.ValidDateFormat;
import recordstore.validation.MultipartFileSize;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "Field is mandatory")
    private Format format;

    private String img = "noImageAvailable.png";

    @MultipartFileSize
    private MultipartFile data;

    @NotEmpty(message = "Field is mandatory")
    private Set<Artist> artists = new HashSet<>();

    @NotEmpty(message = "Field is mandatory")
    private Set<Genre> genres = new HashSet<>();

    @NotNull(message = "Field is mandatory")
    private Label label;

    @Valid
    private List<TrackDTO> tracklist = new ArrayList<>();

    @Valid
    private List<YouTubeVideoDTO> playlist = new ArrayList<>();
}