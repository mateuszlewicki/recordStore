package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.LabelDTO;
import recordstore.mapper.LabelMapper;
import recordstore.service.LabelService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/admin/labels")
public class AdminLabelsController {

    private final LabelService service;
    private final LabelMapper mapper;

    public AdminLabelsController(LabelService service, LabelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping()
    public Page<LabelDTO> showAllLabels(Pageable pageable) {
        return mapper.toDTOs(service.getAllLabels(pageable));
    }

    @PostMapping()
    public ResponseEntity<String> addLabel(@Valid LabelDTO labelDTO) throws IOException {
        service.createLabel(labelDTO);
        return ResponseEntity.ok("Label has been created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLabel(@Valid LabelDTO labelDTO, @PathVariable long id) throws IOException {
        service.updateLabel(labelDTO, id);
        return ResponseEntity.ok("Label has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteLabel(@PathVariable long id) throws IOException {
        service.deleteLabel(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}