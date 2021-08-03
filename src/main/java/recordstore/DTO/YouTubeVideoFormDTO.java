package recordstore.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
public class YouTubeVideoFormDTO {

    private long id;

    @NotBlank(message = "Field is mandatory")
    @Pattern(regexp = "^([A-Za-z0-9-_]*)", message = "Only Video Id")
    String videoId;
}