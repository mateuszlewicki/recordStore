package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.AccountDTO;
import recordstore.DTO.UpdateAccountDTO;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;
import recordstore.error.AccountAlreadyExistException;
import recordstore.error.TokenNotFoundException;
import recordstore.repository.AccountRepository;
import recordstore.repository.VerificationTokenRepository;
import recordstore.utils.FileService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final VerificationTokenRepository tokenRepository;

    private final FileService fileService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, VerificationTokenRepository tokenRepository, FileService fileService) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.fileService = fileService;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Account getAccount(long id) {
        return accountRepository.getOne(id);
    }

    @Override
    public List<Account> getAllUsers() {
        return accountRepository.findAll();
    }

    @Override
    public boolean isPresent(long id) {
        return accountRepository.existsById(id);
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
    public void updateAccount(UpdateAccountDTO accountDTO) throws IOException {
        Account account = accountRepository.getOne(accountDTO.getId());

        if(!accountDTO.getData().isEmpty()) {
            String filename = createUniqueName(accountDTO.getData());
            String removePicture = account.getImg();
            fileService.saveFile(filename, accountDTO.getData());
            fileService.deleteFile(removePicture);
            account.setImg(filename);
        }
        account.setUsername(accountDTO.getUsername());
        accountRepository.save(account);
    }

    @Override
    public void deleteUser(long id) {
        if (accountRepository.findById(id).isPresent()) {
            VerificationToken token = tokenRepository.findByAccount_Id(id);
            tokenRepository.delete(token);
            accountRepository.deleteById(id);
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
        return tokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        if (tokenRepository.existsVerificationTokenByToken(existingToken)) {
            VerificationToken token = tokenRepository.findByToken(existingToken);
            token.updateToken();
            return tokenRepository.save(token);
        } else {
            throw new TokenNotFoundException("Token not found");
        }
    }

    private boolean emailExists(final String email) {
        return accountRepository.existsAccountByEmail(email);
    }

    private String createUniqueName (MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + file.getOriginalFilename();
    }
}