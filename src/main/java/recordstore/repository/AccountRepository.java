package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recordstore.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByUserName(String username);
}
