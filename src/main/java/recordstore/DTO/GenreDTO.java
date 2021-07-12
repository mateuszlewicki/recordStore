package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
public class GenreDTO {

    private long id;

    private String title;

    private Set<ReleaseSlimDTO> releases = new HashSet<>();
}
