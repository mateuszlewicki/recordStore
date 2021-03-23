package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<LabelProjection> findAllBy();

    @Query(value = "SELECT title FROM labels WHERE title LIKE :keyword'%'", nativeQuery = true)
    List<String> search(@Param("keyword") String keyword);
    Label findLabelByTitle(String title);
}
