package recordstore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.entity.Release;

import java.util.List;

public interface ReleaseRepository extends JpaRepository<Release, Long> {

    @EntityGraph(attributePaths = {"genres", "label", "artists", "tracks", "videos"}, type= EntityGraph.EntityGraphType.FETCH)
    Release findReleaseById(long id);

    @Query("SELECT r.id FROM Release r")
    List<Long> findIds(Pageable pageable);

    @Query("SELECT r.id FROM Release r WHERE r.title LIKE %:keyword%")
    List<Long> search(@Param("keyword") String keyword, Pageable pageable);

    @EntityGraph(attributePaths = {"genres", "label", "artists"}, type= EntityGraph.EntityGraphType.FETCH)
    List<Release> findAllByIdIn(List<Long> ids);
}