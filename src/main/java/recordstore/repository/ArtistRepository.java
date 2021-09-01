package recordstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("SELECT a.id AS id, a.name AS name, a.img AS img FROM Artist a WHERE a.name LIKE :keyword%")
    Page<ArtistProjection> search(@Param("keyword") String keyword, Pageable pageable);

    Page<ArtistProjection> findAllBy(Pageable pageable);

    @Query("SELECT a FROM Artist a WHERE a.id = :id")
    Artist findArtistById(@Param("id") long id);
}