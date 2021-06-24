package recordstore.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import recordstore.DTO.AccountDTO;
import recordstore.DTO.ArtistDTO;
import recordstore.DTO.CreateAccountDTO;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.Artist;

@Component
public class AccountMapper {

    public Account fromDTO(CreateAccountDTO accountDTO) {
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

    public AccountDTO accountToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setImg(account.getImg());
        dto.setCollection(account.getCollection());
        dto.setWantlist(account.getWantlist());
        return dto;
    }

    public Page<AccountDTO> toDTOs(Page<Account> accounts) {
        return  accounts.map(this::accountToDTO);
    }
}