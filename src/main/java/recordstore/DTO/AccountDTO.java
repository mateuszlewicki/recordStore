package recordstore.DTO;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AccountDTO {

    private long id;

    private String username;

    private String img;

    private Set<ReleaseDTO> collection = new HashSet<>();
}
