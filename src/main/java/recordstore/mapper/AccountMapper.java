package recordstore.mapper;

import org.springframework.stereotype.Component;
import recordstore.DTO.AccountDTO;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;

@Component
public class AccountMapper {

    public Account fromDTO(AccountDTO accountDTO) {
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setEmail(accountDTO.getEmail());
        account.setImg(accountDTO.getImg());
        return account;
    }

    public UpdateAccountDTO toDTO(Account account) {
        UpdateAccountDTO dto = new UpdateAccountDTO();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setImg(account.getImg());
        return dto;
    }
}