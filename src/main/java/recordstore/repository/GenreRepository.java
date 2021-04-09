package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.entity.Genre;
import recordstore.projections.GenreProjection;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query(value = "SELECT id, title FROM genres WHERE title LIKE :keyword% LIMIT 5", nativeQuery = true)
    List<GenreProjection> findAllBy(@Param("keyword") String keyword);
}
