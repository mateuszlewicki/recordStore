package recordstore.service;

import recordstore.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAllUsers();
    boolean isPresent(long id);
    boolean saveUser(Account account);
    void deleteUser(long id);
}