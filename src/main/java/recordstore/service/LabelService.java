package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;

import java.util.List;

public interface LabelService {

    void saveLabel(Label label);
    void deleteLabel(long id);

    Label getLabel(long id);
    List<LabelProjection> getAllLabelsTitles();
    Page<Label> getAllLabels(Pageable pageable);
}