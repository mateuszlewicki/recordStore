package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import recordstore.entity.Release;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
public class GenreDTO {

    private long id;

    @NotBlank(message = "Field is mandatory")
    private String title;

    private Set<Release> releases = new HashSet<>();
}
