package recordstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;

public interface LabelRepository extends JpaRepository<Label, Long> {

    @Query("SELECT l.id AS id, l.title AS title, l.img AS img FROM Label l WHERE l.title LIKE :keyword%")
    Page<LabelProjection> search(@Param("keyword") String keyword, Pageable pageable);

    Page<LabelProjection> findAllBy(Pageable pageable);

    @Query("SELECT l FROM Label l WHERE l.id = :id")
    Label findLabelById(@Param("id") long id);
}