package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.Release;
import recordstore.entity.VerificationToken;

import java.io.IOException;

public interface AccountService {

    Account getAccount(long id);
    Page<Account> getAllUsers(Pageable pageable);
    Account createNewAccount(Account account);
    void saveRegisterUser(Account account);
    void updateAccount(UpdateAccountDTO dto) throws IOException;
    VerificationToken createVerificationToken(Account account);
    VerificationToken getVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String existingToken);
    void deleteAccount(long id) throws IOException;
    void addReleaseToCollection(long id, Release release);
    void addReleaseToWantlist(long id, Release release);
    void removeReleaseFromCollection(long id, Release release);
    void removeReleaseFromWantlist(long id, Release release);
}