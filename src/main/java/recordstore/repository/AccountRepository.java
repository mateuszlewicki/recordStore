package recordstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.DTO.AccountDTO;
import recordstore.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Account findAccountById(@Param("id") long id);

    Page<AccountDTO> findAllBy(Pageable pageable);

    Account findByEmail(String email);
}