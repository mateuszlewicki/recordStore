package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ReleaseDTO;
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
    public Page<ReleaseDTO> showAllReleases(Pageable pageable){
        return service.getAllReleases(pageable).map(mapStructMapper::releaseToReleaseDTO);
    }

    @GetMapping("/label/{id}")
    public Page<ReleaseDTO> showAllReleasesByLabel(@PathVariable long id, Pageable pageable){
        return service.getReleasesByLabel(id, pageable).map(mapStructMapper::releaseToReleaseDTO);
    }

    @GetMapping("/genre/{id}")
    public Page<ReleaseDTO> showAllReleasesByGenre(@PathVariable long id, Pageable pageable){
        return service.getReleasesByGenre(id, pageable).map(mapStructMapper::releaseToReleaseDTO);
    }

    @GetMapping("/artist/{id}")
    public Page<ReleaseDTO> showAllReleasesByArtist(@PathVariable long id, Pageable pageable){
        return service.getReleasesByArtist(id, pageable).map(mapStructMapper::releaseToReleaseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReleaseDTO> showReleaseInfo(@PathVariable long id){
        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.getRelease(id));
        return ResponseEntity.ok(releaseDTO);
    }

    @GetMapping("/search")
    public Page<ReleaseDTO> showSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
        return service.search(keyword, pageable).map(mapStructMapper::releaseToReleaseDTO);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}