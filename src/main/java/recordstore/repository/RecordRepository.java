package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recordstore.entity.Record;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByGenre(String genre);
    List<Record> findAllByArtist(String artist);

    @Query(value = "select distinct genre from releases", nativeQuery = true)
    List<String> findAllGenres();
}


