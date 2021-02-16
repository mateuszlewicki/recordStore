package recordstore.service;

import recordstore.entity.Account;
import recordstore.entity.VerificationToken;

import java.util.List;

public interface AccountService {

    List<Account> getAllUsers();
    boolean isPresent(long id);
    Account createNewAccount(Account account);
    void saveRegisterUser(Account account);
    void createVerificationToken(Account account, String token);
    VerificationToken getVerificationToken(String token);
    void deleteUser(long id);
}