package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ReleaseDTO;
import recordstore.DTO.ReleaseSlimDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.ReleaseService;

@RestController
@RequestMapping("/releases")
public class ReleasesController {

    private final ReleaseService service;
    private final MapStructMapper mapStructMapper;

    public ReleasesController(ReleaseService service, MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    public Page<ReleaseSlimDTO> getAllReleases(Pageable pageable){
        return service.getAllReleases(pageable).map(mapStructMapper::releaseToReleaseSlimDTO);
    }

        @GetMapping("/search")
    public Page<ReleaseSlimDTO> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
            return service.search(keyword, pageable).map(mapStructMapper::releaseToReleaseSlimDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReleaseDTO> getReleaseInfo(@PathVariable long id){
        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.getRelease(id));
        return ResponseEntity.ok(releaseDTO);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}