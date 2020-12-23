package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recordstore.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}