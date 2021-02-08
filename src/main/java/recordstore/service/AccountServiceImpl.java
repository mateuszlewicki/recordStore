package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import recordstore.entity.Account;
import recordstore.repository.AccountRepository;

import java.util.List;

@Service
public class AccountServiceImpl implements UserDetailsService, AccountService {

    private final AccountRepository accountRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return account;
    }

    public List<Account> getAllUsers() {
        return accountRepository.findAll();
    }

    public boolean isPresent(long id) {
        return accountRepository.existsById(id);
    }

    public boolean saveUser(Account account) {
        Account userFromDb = accountRepository.findByUsername(account.getUsername());

        if (userFromDb != null) {
            return false;
        }
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return true;
    }

    public void deleteUser(long id) {
        if (accountRepository.findById(id).isPresent()) {
            accountRepository.deleteById(id);
        }
    }
}