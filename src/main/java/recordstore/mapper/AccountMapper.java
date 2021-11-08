package recordstore.mapper;

import org.springframework.stereotype.Component;
import recordstore.DTO.AccountDTO;
import recordstore.entity.Account;

@Component
public class AccountMapper {

    public AccountDTO toDTO(Account account) {
        return new AccountDTO(account.getId(), account.getUsername(),account.getEmail());
    }
}