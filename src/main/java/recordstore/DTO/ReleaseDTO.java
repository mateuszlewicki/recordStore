package recordstore.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ReleaseDTO {

    private long id;
    private String title;
    private String releaseDate;

    public ReleaseDTO(long id, String title, String releaseDate) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
    }
}
