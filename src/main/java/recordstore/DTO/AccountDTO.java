package recordstore.DTO;

import lombok.Getter;
import lombok.Setter;
import recordstore.entity.Release;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AccountDTO {

    private long id;

    private String username;

    private String img;

    private Set<Release> collection = new HashSet<>();

    private Set<Release> wantlist = new HashSet<>();
}
