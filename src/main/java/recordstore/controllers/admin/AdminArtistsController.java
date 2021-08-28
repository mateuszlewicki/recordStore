package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recordstore.DTO.ArtistDTO;
import recordstore.DTO.ArtistFormDTO;
import recordstore.DTO.ArtistSlimDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.ArtistService;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/admin/artists")
public class AdminArtistsController {

    private final ArtistService service;
    private final MapStructMapper mapStructMapper;

    public AdminArtistsController(ArtistService service, MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    public Page<ArtistSlimDTO> getAllArtists(Pageable pageable) {
        return service.getAllArtists(pageable).map(mapStructMapper::artistProjectionToArtistSlimDTO);
    }

    @GetMapping("/search")
    public Page<ArtistSlimDTO> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
        return service.search(keyword, pageable).map(mapStructMapper::artistProjectionToArtistSlimDTO);
    }

    @PostMapping()
    public ResponseEntity<ArtistDTO> addArtist(@Valid @RequestBody ArtistFormDTO artistDTO) throws IOException {
        ArtistDTO createdArtistDTO = mapStructMapper.artistToArtistDTO(service.createArtist(artistDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdArtistDTO.getId()).toUri();
        return ResponseEntity.ok(createdArtistDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(@Valid @RequestBody ArtistFormDTO artistDTO, @PathVariable long id) throws IOException {
        ArtistDTO updatedArtistDTO = mapStructMapper.artistToArtistDTO(service.updateArtist(artistDTO, id));
        return ResponseEntity.ok(updatedArtistDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteArtist(@PathVariable long id) throws IOException {
        service.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtistDTO> uploadImage(@PathVariable("id") long id,
                            @RequestParam("file") MultipartFile file) {
        ArtistDTO artistDTO = mapStructMapper.artistToArtistDTO(service.uploadImage(id, file));
        return ResponseEntity.ok(artistDTO);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}