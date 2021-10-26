package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;
import recordstore.service.LabelService;

@RestController
@RequestMapping("/labels")
public class LabelsController {

    private final LabelService service;

    public LabelsController(LabelService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<LabelProjection> getAllLabels(Pageable pageable) {
        return service.getAllLabels(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Label> getLabelInfo(@PathVariable long id){
        return ResponseEntity.ok(service.getLabel(id));
    }

    @GetMapping("/search")
    public Page<LabelProjection> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
        return service.search(keyword, pageable);
    }

//    @GetMapping("/{id}/releases")
//    public Page<ReleaseProjection> getAllReleasesByLabel(@PathVariable long id, Pageable pageable){
//        return releaseService.getReleasesByLabel(id, pageable);
//    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}