package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.CreateAccountDTO;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;
import recordstore.error.*;
import recordstore.repository.AccountRepository;
import recordstore.repository.ReleaseRepository;
import recordstore.repository.VerificationTokenRepository;
import recordstore.utils.FileStore;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Calendar;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final VerificationTokenRepository tokenRepository;
    private final ReleaseRepository releaseRepository;

    private FileStore fileStore;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, VerificationTokenRepository tokenRepository, ReleaseRepository releaseRepository) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.releaseRepository = releaseRepository;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setFileStore(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    @Override
    public Account getAccount(long id) {
        if (!accountRepository.existsById(id)){
            throw new EntityNotFoundException("Account not found");
        }
        return accountRepository.getOne(id);
    }

    @Override
    public Page<Account> getAllUsers(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account createNewAccount(CreateAccountDTO accountDTO) {
        if (emailExists(accountDTO.getEmail())){
            throw new AccountAlreadyExistException("User with the same email already exists.");
        }
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(accountDTO.getPassword()));
        account.setEmail(accountDTO.getEmail());
        return accountRepository.save(account);
    }

    @Override
    public void saveRegisterUser(Account account) {
        account.setEnabled(true);
        accountRepository.save(account);
    }

    @Override
    public Account updateAccount(long id, UpdateAccountDTO accountDTO) {
        if(getPrincipalId() != id) {
            throw new RuntimeException("You tried to update your account with wrong id");
        }
        if (!accountRepository.existsById(id)){
            throw new EntityNotFoundException("Account not found");
        }
        Account account = accountRepository.getOne(id);
        account.setUsername(accountDTO.getUsername());
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(long id) {
        if (!accountRepository.existsById(id)){
            throw new EntityNotFoundException("Account not found");
        }
        VerificationToken token = tokenRepository.findByAccount_Id(id);
        String removePicture = accountRepository.getOne(id).getImg();
        tokenRepository.delete(token);
        accountRepository.deleteById(id);
        fileStore.deleteFile(removePicture);
    }

    @Override
    public Account uploadImage(long id, MultipartFile file) {
        if(getPrincipalId() != id) {
            throw new RuntimeException("You tried to update your account with wrong id");
        }
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account not found");
        }
        Account account = accountRepository.getOne(id);
        String fileName = fileStore.save(file);
        fileStore.deleteFile(account.getImg());
        account.setImg(fileName);
        return accountRepository.save(account);
    }

    @Override
    public byte[] downloadImage(long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account not found");
        }
        Account account = accountRepository.getOne(id);
        return fileStore.download(account.getImg());
    }

    @Override
    public VerificationToken createVerificationToken(Account account) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setAccount(account);
        return tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new EntityNotFoundException("Token not found");
        }
        if (tokenExpired(verificationToken) && !verificationToken.getAccount().isEnabled()) {
            throw new TokenExpiredException(verificationToken.getToken());
        }
        return verificationToken;
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        VerificationToken token = tokenRepository.findByToken(existingToken);
        if (token == null) {
            throw new EntityNotFoundException("Token not found");
        }
        if (token.getAccount().isEnabled()) {
            throw new AccountAlreadyActivatedException("Account already activated");
        }
        token.updateToken();
        return tokenRepository.save(token);
    }

    @Override
    public Account addReleaseToCollection(long userId, long releaseId) {
        if (!accountRepository.existsById(userId)) {
            throw new EntityNotFoundException("Account not found");
        }
        if (!releaseRepository.existsById(releaseId)) {
            throw new EntityNotFoundException("Release not found");
        }
        Account account = accountRepository.getOne(userId);
        account.getCollection().add(releaseRepository.getOne(releaseId));
        return accountRepository.save(account);
    }

    @Override
    public Account removeReleaseFromCollection(long userId, long releaseId) {
        if (!accountRepository.existsById(userId)) {
            throw new EntityNotFoundException("Account not found");
        }
        if (!releaseRepository.existsById(releaseId)) {
            throw new EntityNotFoundException("Release not found");
        }
        Account account = accountRepository.getOne(userId);
        account.getCollection().remove(releaseRepository.getOne(releaseId));
        return accountRepository.save(account);
    }

    private boolean emailExists(final String email) {
        return accountRepository.existsAccountByEmail(email);
    }

    private boolean tokenExpired(VerificationToken token) {
        Calendar calendar = Calendar.getInstance();
        return token.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0;
    }

    private long getPrincipalId() {
        return ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}