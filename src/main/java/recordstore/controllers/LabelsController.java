package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.LabelDTO;
import recordstore.DTO.LabelSlimDTO;
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
    public Page<LabelSlimDTO> getAllLabels(Pageable pageable) {
        return service.getAllLabels(pageable).map(mapStructMapper::labelProjectionToLabelSlimDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelDTO> getLabelInfo(@PathVariable long id){
        LabelDTO labelDTO = mapStructMapper.labelToLabelDTO(service.getLabel(id));
        return ResponseEntity.ok(labelDTO);
    }

    @GetMapping("/search")
    public Page<LabelSlimDTO> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
        return service.search(keyword, pageable).map(mapStructMapper::labelProjectionToLabelSlimDTO);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}