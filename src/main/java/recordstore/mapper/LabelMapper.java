package recordstore.mapper;

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

    public Label fromDTO(LabelDTO labelDTO) {
        Label label = new Label();
        label.setId(labelDTO.getId());
        label.setTitle(labelDTO.getTitle());
        label.setCountry(labelDTO.getCountry());
        label.setDescription(labelDTO.getDescription());
        label.setImg(labelDTO.getImg());
        label.setData(labelDTO.getData());
        label.setReleases(label.getReleases());
        return label;
    }
}