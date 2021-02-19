package recordstore.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recordstore.entity.Account;
import recordstore.repository.AccountRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            Account account = accountRepository.findByEmail(email);
            if (account == null) {
                throw new UsernameNotFoundException("No user found with the email: " + email);
            }
            return account;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}