package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recordstore.DTO.ArtistFormDTO;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;
import recordstore.service.ArtistService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin/artists")
public class AdminArtistsController {

  private final ArtistService service;

    public AdminArtistsController(ArtistService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<ArtistProjection> getAllArtists(Pageable pageable) {
        return service.getAllArtists(pageable);
    }

    @GetMapping("/search")
    public Page<ArtistProjection> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
        return service.search(keyword, pageable);
    }

    @PostMapping()
    public ResponseEntity<Artist> addArtist(@Valid @RequestBody ArtistFormDTO artistDTO) {
        Artist created = service.createArtist(artistDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@Valid @RequestBody ArtistFormDTO artistDTO, @PathVariable long id) {
        Artist updated = service.updateArtist(artistDTO, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteArtist(@PathVariable long id) {
        service.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artist> uploadImage(@PathVariable("id") long id,
                            @RequestParam("file") MultipartFile file) {
        Artist artist = service.uploadImage(id, file);
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}