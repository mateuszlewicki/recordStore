package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.AccountDTO;
import recordstore.DTO.CreateAccountDTO;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;

public interface AccountService {

    Account getAccount(long id);
    Account getAccountByEmail(String email);
    Page<AccountDTO> getAllAccounts(Pageable pageable);

    Account createAccount(CreateAccountDTO account);
    Account createAccountWithFacebook(Account account);
    void enableAccount(String token);
    Account updateAccount(long id, UpdateAccountDTO dto);
    void deleteAccount(long id);

    Account uploadImage(long id, MultipartFile file);
    byte[] downloadImage(long id);

    // The handling of verification token
    VerificationToken createVerificationToken(Account account);
    VerificationToken getVerificationToken(String token);
    VerificationToken regenerateToken(String existingToken);

    // The handling of associations
    Account addReleaseToCollection(long userId, long releaseId);
    Account removeReleaseFromCollection(long userId, long releaseId);
}