package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.DTO.CreateAccountDTO;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.Release;
import recordstore.entity.VerificationToken;

import java.io.IOException;

public interface AccountService {

    Account getAccount(long id);
    Page<Account> getAllUsers(Pageable pageable);

    Account createNewAccount(CreateAccountDTO account);
    void saveRegisterUser(Account account);
    void updateAccount(long id, UpdateAccountDTO dto) throws IOException;
    void deleteAccount(long id) throws IOException;

    VerificationToken createVerificationToken(Account account);
    VerificationToken getVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String existingToken);

    // managing user collections
    void addReleaseToCollection(long id, Release release);
    void addReleaseToWantlist(long id, Release release);
    void removeReleaseFromCollection(long id, Release release);
    void removeReleaseFromWantlist(long id, Release release);
}