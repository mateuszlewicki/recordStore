package recordstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.DTO.ArtistDTO;
import recordstore.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("SELECT new recordstore.DTO.ArtistDTO(a.id, a.name) FROM Artist a WHERE a.name LIKE :keyword%")
    Page<ArtistDTO> search(@Param("keyword") String keyword, Pageable pageable);

    Page<ArtistDTO> findAllBy(Pageable pageable);

    @Query("SELECT a FROM Artist a WHERE a.id = :id")
    Artist findArtistById(@Param("id") long id);
}