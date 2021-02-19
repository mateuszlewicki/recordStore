package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import recordstore.entity.Account;
import recordstore.entity.VerificationToken;
import recordstore.error.AccountAlreadyExistException;
import recordstore.error.TokenNotFoundException;
import recordstore.repository.AccountRepository;
import recordstore.repository.VerificationTokenRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final VerificationTokenRepository tokenRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, VerificationTokenRepository tokenRepository) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
}