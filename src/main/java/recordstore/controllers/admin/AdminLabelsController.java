package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recordstore.DTO.LabelDTO;
import recordstore.DTO.LabelFormDTO;
import recordstore.entity.Label;
import recordstore.service.LabelService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin/labels")
public class AdminLabelsController {

   private final LabelService service;

    public AdminLabelsController(LabelService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<LabelDTO> getAllLabels(Pageable pageable) {
        return service.getAllLabels(pageable);
    }

    @GetMapping("/search")
    public Page<LabelDTO> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
        return service.search(keyword, pageable);
    }

    @PostMapping()
    public ResponseEntity<Label> addLabel(@Valid @RequestBody LabelFormDTO labelDTO) {
        Label created = service.createLabel(labelDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Label> updateLabel(@Valid @RequestBody LabelFormDTO labelDTO, @PathVariable long id) {
        Label updated = service.updateLabel(labelDTO, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLabel(@PathVariable long id) {
        service.deleteLabel(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Label> uploadImage(@PathVariable("id") long id,
                            @RequestParam("file") MultipartFile file) {
        Label label = service.uploadImage(id, file);
        return ResponseEntity.ok(label);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}