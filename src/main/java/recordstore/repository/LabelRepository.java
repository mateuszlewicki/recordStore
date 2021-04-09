package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {

    @Query(value = "SELECT id, title FROM labels WHERE title LIKE :keyword% LIMIT 5", nativeQuery = true)
    List<LabelProjection> findAllBy(@Param("keyword") String keyword);

    @Query(value = "SELECT title FROM labels WHERE title LIKE :keyword%", nativeQuery = true)
    List<String> search(@Param("keyword") String keyword);
    Label findLabelByTitle(String title);
}
