package recordstore.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LabelDTO {

    private long id;
    private String title;

    public LabelDTO(long id, String title) {
        this.id = id;
        this.title = title;
    }
}
