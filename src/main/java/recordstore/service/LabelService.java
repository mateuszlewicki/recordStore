package recordstore.service;

import recordstore.entity.Label;
import recordstore.projections.LabelProjection;

import java.util.List;

public interface LabelService {

    void saveLabel(Label label);
    void deleteLabel(long id);

    Label getLabel(long id);
    List<LabelProjection> findAllLabels();
}
