package recordstore.service;

import recordstore.entity.Account;
import recordstore.entity.VerificationToken;

import java.util.List;

public interface AccountService {

    List<Account> getAllUsers();
    boolean isPresent(long id);
    Account createNewAccount(Account account);
    void saveRegisterUser(Account account);
    VerificationToken createVerificationToken(Account account);
    VerificationToken getVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String existingToken);
    void deleteUser(long id);
}