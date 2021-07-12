package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recordstore.DTO.LabelDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.LabelService;

@RestController
@RequestMapping("/labels")
public class LabelsController {

    private final LabelService service;
    private final MapStructMapper mapStructMapper;

    public LabelsController(LabelService service, MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    public Page<LabelDTO> showAllLabels(Pageable pageable) {
        return service.getAllLabels(pageable).map(mapStructMapper::labelToLabelDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelDTO> showLabelInfo(@PathVariable long id){
        LabelDTO labelDTO = mapStructMapper.labelToLabelDTO(service.getLabel(id));
        return ResponseEntity.ok(labelDTO);
    }
}