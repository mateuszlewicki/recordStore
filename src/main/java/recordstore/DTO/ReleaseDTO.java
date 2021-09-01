package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import recordstore.enums.Format;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ReleaseDTO {

    private long id;

    private String code;

    private String title;

    private String releaseDate;

    private Format format;

    private String img;

    private Set<ArtistSlimDTO> artists = new HashSet<>();

    private Set<GenreSlimDTO> genres = new HashSet<>();

    private LabelSlimDTO label;

    private Set<TrackDTO> tracks = new HashSet<>();

    private Set<YouTubeVideoDTO> videos = new HashSet<>();
}