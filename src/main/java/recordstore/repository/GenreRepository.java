package recordstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("SELECT g FROM Genre g")
    Page<Genre> findAllGenres(Pageable pageable);

    @Query("SELECT g FROM Genre g WHERE g.id = :id")
    Genre findGenreById(@Param("id") long id);
}