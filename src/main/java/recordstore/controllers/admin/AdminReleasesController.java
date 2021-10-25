package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recordstore.DTO.ReleaseDTO;
import recordstore.DTO.ReleaseFormDTO;
import recordstore.DTO.ReleaseSlimDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.ReleaseService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/admin/releases")
public class AdminReleasesController {

    private final ReleaseService service;
    private final MapStructMapper mapStructMapper;

    public AdminReleasesController(ReleaseService service, MapStructMapper mapStructMapper) {
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

    @PostMapping()
    public ResponseEntity<ReleaseDTO> addRelease(@Valid @RequestBody ReleaseFormDTO releaseDTO) throws IOException {
        ReleaseDTO createdReleaseDTO = mapStructMapper.releaseToReleaseDTO(service.createRelease(releaseDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdReleaseDTO.getId()).toUri();
        return ResponseEntity.ok(createdReleaseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReleaseDTO> updateRelease(@Valid @RequestBody ReleaseFormDTO releaseDTO,
                                                @PathVariable long id) throws IOException {
        ReleaseDTO updatedReleaseDTO = mapStructMapper.releaseToReleaseDTO(service.updateRelease(releaseDTO, id));
        return ResponseEntity.ok(updatedReleaseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRelease(@PathVariable long id) throws IOException {
        service.deleteRelease(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleaseDTO> uploadImage(@PathVariable("id") long id,
                            @RequestParam("file")MultipartFile file) {
        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.uploadImage(id, file));
        return ResponseEntity.ok(releaseDTO);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }

    // The handling of associations
//    @PatchMapping("/{releaseId}/labels/{labelId}")
//    public ResponseEntity<ReleaseDTO> addLabelToRelease(@PathVariable("releaseId") long releaseId,
//                                                        @PathVariable("labelId") long labelId){
//        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.addLabelToRelease(releaseId, labelId));
//        return ResponseEntity.ok(releaseDTO);
//    }
//
//    @PatchMapping("/{releaseId}/artists/{artistId}")
//    public ResponseEntity<ReleaseDTO> addArtistToRelease(@PathVariable("releaseId") long releaseId,
//                                                        @PathVariable("artistId") long artistId){
//        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.addArtistToRelease(releaseId, artistId));
//        return ResponseEntity.ok(releaseDTO);
//    }
//
//    @PatchMapping("/{releaseId}/genres/{genreId}")
//    public ResponseEntity<ReleaseDTO> addGenreToRelease(@PathVariable("releaseId") long releaseId,
//                                                         @PathVariable("genreId") long genreId){
//        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.addGenreToRelease(releaseId, genreId));
//        return ResponseEntity.ok(releaseDTO);
//    }
//
//    @DeleteMapping("/{releaseId}/labels/{labelId}")
//    public ResponseEntity<ReleaseDTO> removeLabelFromRelease(@PathVariable("releaseId") long releaseId,
//                                                        @PathVariable("labelId") long labelId){
//        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.removeLabelFromRelease(releaseId, labelId));
//        return ResponseEntity.ok(releaseDTO);
//    }
//
//    @DeleteMapping("/{releaseId}/artists/{artistId}")
//    public ResponseEntity<ReleaseDTO> removeArtistFromRelease(@PathVariable("releaseId") long releaseId,
//                                                             @PathVariable("artistId") long artistId){
//        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.removeArtistFromRelease(releaseId, artistId));
//        return ResponseEntity.ok(releaseDTO);
//    }
//
//    @DeleteMapping("/{releaseId}/genres/{genreId}")
//    public ResponseEntity<ReleaseDTO> removeLabelFromRelease(@PathVariable("releaseId") long releaseId,
//                                                             @PathVariable("genreId") long genreId){
//        ReleaseDTO releaseDTO = mapStructMapper.releaseToReleaseDTO(service.removeGenreFromRelease(releaseId, genreId));
//        return ResponseEntity.ok(releaseDTO);
//    }
}