package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import recordstore.entity.Account;
import recordstore.entity.Role;
import recordstore.repository.AccountRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountDetailsServiceImpl(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
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

    public Account findUserById(long id) {
        Optional<Account> userFromDb = accountRepository.findById(id);
        return userFromDb.orElse(new Account());
    }

    public List<Account> allUsers() {
        return accountRepository.findAll();
    }

    public boolean saveUser(Account account) {
        Account userFromDb = accountRepository.findByUsername(account.getUsername());

        if (userFromDb != null) {
            return false;
        }
        account.setRoles(Collections.singleton(new Role(1l, "ROLE_USER")));
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return true;
    }

    public boolean deleteUser(long id) {
        if (accountRepository.findById(id).isPresent()) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
