package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Release;
import recordstore.projections.ReleaseProjection;
import recordstore.service.ReleaseService;

@RestController
@RequestMapping("/releases")
public class ReleasesController {

    private final ReleaseService service;

    public ReleasesController(ReleaseService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<ReleaseProjection> getAllReleases(Pageable pageable){
        return service.getAllReleases(pageable);
    }

        @GetMapping("/search")
    public Page<ReleaseProjection> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
            return service.search(keyword, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Release> getReleaseInfo(@PathVariable long id){
        Release release = service.getRelease(id);
        return ResponseEntity.ok(release);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}