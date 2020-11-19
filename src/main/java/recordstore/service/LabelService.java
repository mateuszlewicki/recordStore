package recordstore.service;

import recordstore.projections.LabelProjection;

import java.util.List;

public interface LabelService {

    List<LabelProjection> findAllLabels();
}
