package recordstore.mapstruct.mappers;

import org.mapstruct.Mapper;
import recordstore.DTO.AccountDTO;
import recordstore.entity.Account;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    AccountDTO accountToAccountDTO(Account account);
}