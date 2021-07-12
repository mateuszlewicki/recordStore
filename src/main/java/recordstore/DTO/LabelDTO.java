package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class LabelDTO {

    private long id;

    private String title;

    private String country;

    private String description;

    private String img;

    private List<ReleaseSlimDTO> releases = new ArrayList<>();
}