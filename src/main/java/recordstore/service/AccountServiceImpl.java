package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.Release;
import recordstore.entity.VerificationToken;
import recordstore.error.*;
import recordstore.repository.AccountRepository;
import recordstore.repository.VerificationTokenRepository;
import recordstore.utils.FileService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Calendar;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final VerificationTokenRepository tokenRepository;

    private FileService fileService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, VerificationTokenRepository tokenRepository) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
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
    public Account createNewAccount(Account account) {
        if (emailExists(account.getEmail())){
            throw new AccountAlreadyExistException("User with the same email already exists.");
        }
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public void saveRegisterUser(Account account) {
        account.setEnabled(true);
        accountRepository.save(account);
    }

    @Override
    public void updateAccount(long id, UpdateAccountDTO accountDTO) throws IOException {
        Account account = accountRepository.getOne(id);
        if(!accountDTO.getData().isEmpty()) {
            fileService.deleteFile(account.getImg());
            account.setImg(fileService.saveFile(accountDTO.getData()));
        }
        account.setUsername(accountDTO.getUsername());
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(long id) throws IOException {
        if (!accountRepository.existsById(id)){
            throw new EntityNotFoundException("Account not found");
        }
        VerificationToken token = tokenRepository.findByAccount_Id(id);
        String removePicture = accountRepository.getOne(id).getImg();
        tokenRepository.delete(token);
        accountRepository.deleteById(id);
        fileService.deleteFile(removePicture);
    }

    @Override
    public void addReleaseToCollection(long id, Release release) {
        Account account = accountRepository.getOne(id);
        if (!account.getCollection().contains(release)) {
            account.addToCollection(release);
            accountRepository.save(account);
        }
    }

    @Override
    public void addReleaseToWantlist(long id, Release release) {
        Account account = accountRepository.getOne(id);
        if (!account.getWantlist().contains(release)) {
            account.addToWantlist(release);
            accountRepository.save(account);
        }
    }

    @Override
    public void removeReleaseFromCollection(long id, Release release) {
        Account account = accountRepository.getOne(id);
        if (account.getCollection().contains(release)) {
            account.removeFromCollection(release);
            accountRepository.save(account);
        }
    }

    @Override
    public void removeReleaseFromWantlist(long id, Release release) {
        Account account = accountRepository.getOne(id);
        if (account.getWantlist().contains(release)) {
            account.removeFromWantlist(release);
            accountRepository.save(account);
        }
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

    private boolean emailExists(final String email) {
        return accountRepository.existsAccountByEmail(email);
    }

    private boolean tokenExpired(VerificationToken token) {
        Calendar calendar = Calendar.getInstance();
        return token.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0;
    }
}