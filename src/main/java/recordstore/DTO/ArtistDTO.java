package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ArtistDTO {

    private long id;

    private String name;

    private String country;

    private String description;

    private String img;

    private Set<ReleaseSlimDTO> releases = new HashSet<>();
}