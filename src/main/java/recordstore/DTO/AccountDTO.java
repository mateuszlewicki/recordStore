package recordstore.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AccountDTO {

    private long id;
    private String username;
    private String email;

    public AccountDTO(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}