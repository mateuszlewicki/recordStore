package recordstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.entity.Release;
import recordstore.projections.ReleaseProjection;

public interface ReleaseRepository extends JpaRepository<Release, Long> {

    @EntityGraph(attributePaths = {"genres", "label", "artists", "tracks", "videos"}, type= EntityGraph.EntityGraphType.FETCH)
    Release findReleaseById(long id);

    Page<ReleaseProjection> findAllBy(Pageable pageable);

    @Query("SELECT r FROM Release r WHERE r.title LIKE %:keyword%")
    Page<ReleaseProjection> search(@Param("keyword") String keyword, Pageable pageable);

    Page<ReleaseProjection> findAllByLabel_Id(long id, Pageable pageable);
    Page<ReleaseProjection> findAllByGenres_id(long id, Pageable pageable);
    Page<ReleaseProjection> findAllByArtists_id(long id, Pageable pageable);
    Page<ReleaseProjection> findAllByAccounts_id(long id, Pageable pageable);

    boolean existsReleasesByLabel_Id(long id);
    boolean existsReleasesByArtists_id(long id);
    boolean existsReleasesByGenres_id(long id);
}