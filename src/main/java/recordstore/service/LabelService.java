package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.DTO.LabelDTO;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;

import java.io.IOException;
import java.util.List;

public interface LabelService {

    void createLabel(LabelDTO labelDTO) throws IOException;
    void updateLabel(LabelDTO labelDTO, long id) throws IOException;
    void deleteLabel(long id) throws IOException;

    Label getLabel(long id);
    List<LabelProjection> getLabelsTitles(String query);
    Page<Label> getAllLabels(Pageable pageable);

    List<String> search(String keyword);
    Label getLabelByTitle(String title);
}