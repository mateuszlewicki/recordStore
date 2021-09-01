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
        return service.getAllReleases(pageable).map(mapStructMapper::releaseProjectionToReleaseSlimDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReleaseDTO> getReleaseInfo(@PathVariable long id){
        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.getRelease(id));
        return ResponseEntity.ok(releaseDTO);
    }

    @GetMapping("/search")
    public Page<ReleaseSlimDTO> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
        return service.search(keyword, pageable).map(mapStructMapper::releaseProjectionToReleaseSlimDTO);
    }

    @GetMapping("/label/{id}")
    public Page<ReleaseSlimDTO> getAllReleasesByLabel(@PathVariable long id, Pageable pageable){
        return service.getReleasesByLabel(id, pageable).map(mapStructMapper::releaseProjectionToReleaseSlimDTO);
    }

    @GetMapping("/genre/{id}")
    public Page<ReleaseSlimDTO> getAllReleasesByGenre(@PathVariable long id, Pageable pageable){
        return service.getReleasesByGenre(id, pageable).map(mapStructMapper::releaseProjectionToReleaseSlimDTO);
    }

    @GetMapping("/artist/{id}")
    public Page<ReleaseSlimDTO> getAllReleasesByArtist(@PathVariable long id, Pageable pageable){
        return service.getReleasesByArtist(id, pageable).map(mapStructMapper::releaseProjectionToReleaseSlimDTO);
    }

    @GetMapping("/account/{id}")
    public Page<ReleaseSlimDTO> getAllReleasesByAccount(@PathVariable long id, Pageable pageable){
        return service.getReleasesByAccount(id, pageable).map(mapStructMapper::releaseProjectionToReleaseSlimDTO);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}