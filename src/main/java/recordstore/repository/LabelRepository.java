package recordstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recordstore.DTO.LabelDTO;
import recordstore.entity.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {

    @Query("SELECT new recordstore.DTO.LabelDTO(l.id, l.title) FROM Label l WHERE l.title LIKE :keyword%")
    Page<LabelDTO> search(@Param("keyword") String keyword, Pageable pageable);

    Page<LabelDTO> findAllBy(Pageable pageable);

    @Query("SELECT l FROM Label l WHERE l.id = :id")
    Label findLabelById(@Param("id") long id);
}