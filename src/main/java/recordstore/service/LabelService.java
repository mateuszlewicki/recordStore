package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;

import java.io.IOException;
import java.util.List;

public interface LabelService {

    void saveLabel(Label label) throws IOException;
    void deleteLabel(long id) throws IOException;

    Label getLabel(long id);
    boolean isPresent(long id);
    List<LabelProjection> getLabelsTitles(String query);
    Page<Label> getAllLabels(Pageable pageable);

    List<String> search(String keyword);
    Label getLabelByTitle(String title);
}