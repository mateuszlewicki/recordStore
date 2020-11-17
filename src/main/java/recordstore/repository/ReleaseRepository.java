package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recordstore.entity.Release;

import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {

    List<Release> findAllByGenre(String genre);
    List<Release> findAllByArtist(String artist);

    @Query(value = "select distinct genre from releases", nativeQuery = true)
    List<String> findAllGenres();
}