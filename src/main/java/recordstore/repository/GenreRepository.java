package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recordstore.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}