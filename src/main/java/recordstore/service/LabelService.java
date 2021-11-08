package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.LabelDTO;
import recordstore.DTO.LabelFormDTO;
import recordstore.entity.Label;

public interface LabelService {

    Label getLabel(long id);
    Page<LabelDTO> getAllLabels(Pageable pageable);
    Page<LabelDTO> search(String keyword, Pageable pageable);

    Label createLabel(LabelFormDTO labelDTO);
    Label updateLabel(LabelFormDTO labelDTO, long id);
    void deleteLabel(long id);

    Label uploadImage(long id, MultipartFile file);
    byte[] downloadImage(long id);
}