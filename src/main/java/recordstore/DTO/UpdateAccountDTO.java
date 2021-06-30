package recordstore.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import recordstore.validation.MultipartFileSize;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateAccountDTO {

    @NotBlank(message = "Field is mandatory")
    private String username;

    private String img;

    @MultipartFileSize
    private MultipartFile data;
}