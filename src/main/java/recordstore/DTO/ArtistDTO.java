package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ArtistDTO {

    private long id;

    private String name;

    private String country;

    private String description;

    private String img;
}