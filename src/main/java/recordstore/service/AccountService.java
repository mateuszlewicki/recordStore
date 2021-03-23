package recordstore.service;

import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;

import java.io.IOException;
import java.util.List;

public interface AccountService {

    Account getAccount(long id);
    List<Account> getAllUsers();
    boolean isPresent(long id);
    Account createNewAccount(Account account);
    void saveRegisterUser(Account account);
    void updateAccount(UpdateAccountDTO dto) throws IOException;
    VerificationToken createVerificationToken(Account account);
    VerificationToken getVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String existingToken);
    void deleteUser(long id) throws IOException;
}