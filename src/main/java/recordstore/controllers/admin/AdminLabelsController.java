package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recordstore.DTO.LabelDTO;
import recordstore.DTO.LabelFormDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.LabelService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/admin/labels")
public class AdminLabelsController {

    private final LabelService service;
    private final MapStructMapper mapStructMapper;

    public AdminLabelsController(LabelService service, MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    public Page<LabelDTO> showAllLabels(Pageable pageable) {
        return service.getAllLabels(pageable).map(mapStructMapper::labelToLabelDTO);
    }

    @PostMapping()
    public ResponseEntity<LabelDTO> addLabel(@Valid LabelFormDTO labelDTO) throws IOException {
        LabelDTO createdLabelDTO = mapStructMapper.labelToLabelDTO(service.createLabel(labelDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdLabelDTO.getId()).toUri();
        return ResponseEntity.ok(createdLabelDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelDTO> updateLabel(@Valid LabelFormDTO labelDTO, @PathVariable long id) throws IOException {
        LabelDTO updatedLabelDTO = mapStructMapper.labelToLabelDTO(service.updateLabel(labelDTO, id));
        return ResponseEntity.ok(updatedLabelDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteLabel(@PathVariable long id) throws IOException {
        service.deleteLabel(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}