package recordstore.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ArtistDTO {

    private long id;
    private String name;

    public ArtistDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
