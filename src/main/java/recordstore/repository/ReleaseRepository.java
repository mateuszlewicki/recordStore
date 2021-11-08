package recordstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.DTO.ReleaseDTO;
import recordstore.entity.Release;

public interface ReleaseRepository extends JpaRepository<Release, Long> {

    @EntityGraph(attributePaths = {"genres", "label", "artists", "tracks", "videos"}, type= EntityGraph.EntityGraphType.FETCH)
    Release findReleaseById(long id);

    Page<ReleaseDTO> findAllBy(Pageable pageable);

    @Query("SELECT new recordstore.DTO.ReleaseDTO(r.id, r.title, r.releaseDate) FROM Release r WHERE r.title LIKE %:keyword%")
    Page<ReleaseDTO> search(@Param("keyword") String keyword, Pageable pageable);
}