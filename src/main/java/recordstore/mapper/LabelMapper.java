package recordstore.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import recordstore.DTO.LabelDTO;
import recordstore.entity.Label;

@Component
public class LabelMapper {

    public LabelDTO toDTO(Label label) {
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setId(label.getId());
        labelDTO.setTitle(label.getTitle());
        labelDTO.setCountry(label.getCountry());
        labelDTO.setDescription(label.getDescription());
        labelDTO.setImg(label.getImg());
        labelDTO.setReleases(label.getReleases());
        return labelDTO;
    }

    public Page<LabelDTO> toDTOs(Page<Label> labels) {
        return labels.map(this::toDTO);
    }
}