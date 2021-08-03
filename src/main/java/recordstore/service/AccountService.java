package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.CreateAccountDTO;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;

import java.io.IOException;

public interface AccountService {

    Account getAccount(long id);
    Page<Account> getAllUsers(Pageable pageable);

    Account createNewAccount(CreateAccountDTO account);
    void saveRegisterUser(Account account);
    Account updateAccount(long id, UpdateAccountDTO dto) throws IOException;
    void deleteAccount(long id) throws IOException;

    Account uploadImage(long id, MultipartFile file);
    byte[] downloadImage(long id);

    VerificationToken createVerificationToken(Account account);
    VerificationToken getVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String existingToken);

    Account addReleaseToCollection(long userId, long releaseId);
    Account removeReleaseFromCollection(long userId, long releaseId);
}