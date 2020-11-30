package recordstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    List<LabelProjection> findAllBy();
}
