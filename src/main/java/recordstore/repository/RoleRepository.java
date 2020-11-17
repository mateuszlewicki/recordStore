package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recordstore.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}