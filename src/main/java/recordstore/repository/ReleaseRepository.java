package recordstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.entity.Artist;
import recordstore.entity.Genre;
import recordstore.entity.Label;
import recordstore.entity.Release;

public interface ReleaseRepository extends JpaRepository<Release, Long> {

    @Query(value = "SELECT * FROM releases WHERE title LIKE %:keyword%", nativeQuery = true)
    Page<Release> search(@Param("keyword") String keyword, Pageable pageable);

    Page<Release> findReleasesByGenres_id(long id, Pageable pageable);
    Page<Release> findReleasesByArtists_id(long id, Pageable pageable);
    Page<Release> findReleasesByLabel_Id(long id, Pageable pageable);
    Page<Release> findReleasesByCollections_id(long id, Pageable pageable);

    int countAllByArtists(Artist artist);
    int countAllByGenres(Genre genre);
    int countAllByLabel(Label label);
}