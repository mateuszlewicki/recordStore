package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.AccountDTO;
import recordstore.DTO.CreateAccountDTO;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;
import recordstore.error.AccountAlreadyActivatedException;
import recordstore.error.AccountAlreadyExistException;
import recordstore.error.TokenExpiredException;
import recordstore.repository.AccountRepository;
import recordstore.repository.ReleaseRepository;
import recordstore.repository.VerificationTokenRepository;
import recordstore.service.s3.FileStorage;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final VerificationTokenRepository tokenRepository;
    private final ReleaseRepository releaseRepository;
    private final FileStorage fileStore;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository,
                              VerificationTokenRepository tokenRepository,
                              ReleaseRepository releaseRepository,
                              FileStorage fileStore,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.releaseRepository = releaseRepository;
        this.fileStore = fileStore;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Account getAccount(long id) {
        var account = accountRepository.findAccountById(id);
        if (account == null){
            throw new EntityNotFoundException("Account not found");
        }
        return account;
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Page<AccountDTO> getAllAccounts(Pageable pageable) {
        return accountRepository.findAllBy(pageable);
    }

    @Override
    public Account createAccount(CreateAccountDTO accountDTO) {
        var existedAccount = getAccountByEmail(accountDTO.getEmail());
        if (existedAccount != null){
            throw new AccountAlreadyExistException("User with the same email already exists.");
        }
        var account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(accountDTO.getPassword()));
        account.setEmail(accountDTO.getEmail());
        return accountRepository.save(account);
    }

    @Override
    public Account createAccountWithFacebook(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void enableAccount(String token) {
        var verificationToken = getVerificationToken(token);
        var account = verificationToken.getAccount();
        if (account.isEnabled()) {
            throw new AccountAlreadyActivatedException("Account already activated");
        }
        account.setEnabled(true);
        accountRepository.save(account);
    }

    @Override
    public Account updateAccount(long id, UpdateAccountDTO accountDTO) {
        if(getPrincipalId() != id) {
            throw new RuntimeException("You tried to update your account with wrong id");
        }
        var account = getAccount(id);
        account.setUsername(accountDTO.getUsername());
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(long id) {
        var account = getAccount(id);
        var token = tokenRepository.findByAccount_Id(id);
        var removePicture = account.getImg();
        tokenRepository.delete(token);
        accountRepository.deleteById(id);
        fileStore.deleteFile(removePicture);
    }

    // add new exception for this case
    @Override
    public Account uploadImage(long id, MultipartFile file) {
        if(getPrincipalId() != id) {
            throw new RuntimeException("You tried to update your account with wrong id");
        }
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        var account = getAccount(id);
        var fileName = fileStore.save(file);
        fileStore.deleteFile(account.getImg());
        account.setImg(fileName);
        return accountRepository.save(account);
    }

    @Override
    public byte[] downloadImage(long id) {
        var account = getAccount(id);
        return fileStore.download(account.getImg());
    }

    // The handling of verification token
    @Override
    public VerificationToken createVerificationToken(Account account) {
        var verificationToken = new VerificationToken();
        verificationToken.setAccount(account);
        return tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        var verificationToken = getToken(token);
        if (verificationToken.isExpired()) {
            throw new TokenExpiredException("Token: " + verificationToken.getToken() + " is expired!");
        }
        return verificationToken;
    }

    @Override
    public VerificationToken regenerateToken(String existingToken) {
        var verificationToken = getToken(existingToken);
        if (verificationToken.getAccount().isEnabled()) {
            throw new AccountAlreadyActivatedException("Account already activated");
        }
        verificationToken.updateToken();
        return tokenRepository.save(verificationToken);
    }

    // The handling of associations
    @Override
    public Account addReleaseToCollection(long userId, long releaseId) {
        var account = getAccount(userId);
        account.getCollection().add(releaseRepository.getById(releaseId));
        return accountRepository.save(account);
    }

    @Override
    public Account removeReleaseFromCollection(long userId, long releaseId) {
        var account = getAccount(userId);
        account.getCollection().remove(releaseRepository.getById(releaseId));
        return accountRepository.save(account);
    }

    private VerificationToken getToken(String token) {
        var verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new EntityNotFoundException("Token not found");
        }
        return verificationToken;
    }

    private long getPrincipalId() {
        return ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}