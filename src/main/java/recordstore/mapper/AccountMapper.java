package recordstore.mapper;

import org.springframework.stereotype.Component;
import recordstore.DTO.AccountDTO;
import recordstore.entity.Account;

@Component
public class AccountMapper {

    public Account fromDTO(AccountDTO accountDTO) {
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setEmail(accountDTO.getEmail());
        return account;
    }
}
