package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recordstore.entity.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
