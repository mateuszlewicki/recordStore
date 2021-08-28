package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.LabelFormDTO;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;

import java.io.IOException;

public interface LabelService {

    Label getLabel(long id);
    Page<LabelProjection> getAllLabels(Pageable pageable);
    Page<LabelProjection> search(String keyword, Pageable pageable);

    Label createLabel(LabelFormDTO labelDTO) throws IOException;
    Label updateLabel(LabelFormDTO labelDTO, long id) throws IOException;
    void deleteLabel(long id) throws IOException;

    Label uploadImage(long id, MultipartFile file);
    byte[] downloadImage(long id);
}