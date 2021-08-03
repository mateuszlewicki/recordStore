package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LabelDTO {

    private long id;

    private String title;

    private String country;

    private String description;

    private String img;
}