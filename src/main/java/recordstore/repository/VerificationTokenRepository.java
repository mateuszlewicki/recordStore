package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recordstore.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);
    VerificationToken findByAccount_Id(long id);
}