package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recordstore.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);
    boolean existsAccountByEmail(String email);
}