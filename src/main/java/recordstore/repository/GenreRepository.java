package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recordstore.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByTitle(String title);
}
