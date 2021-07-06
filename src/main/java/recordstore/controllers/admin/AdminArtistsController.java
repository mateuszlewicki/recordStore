package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ArtistDTO;
import recordstore.mapper.ArtistMapper;
import recordstore.service.ArtistService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/admin/artists")
public class AdminArtistsController {

    private final ArtistMapper mapper;
    private final ArtistService service;

    public AdminArtistsController(ArtistMapper mapper, ArtistService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    public Page<ArtistDTO> showAllArtists(Pageable pageable) {
        return mapper.toDTOs(service.getAllArtists(pageable));
    }

    @PostMapping()
    public ResponseEntity<String> addArtist(@Valid ArtistDTO artistDTO) throws IOException {
        service.createArtist(artistDTO);
        return ResponseEntity.ok("Artist has been created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateArtist(@Valid ArtistDTO artistDTO, @PathVariable long id) throws IOException {
        service.updateArtist(artistDTO, id);
        return ResponseEntity.ok("Artist has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteArtist(@PathVariable long id) throws IOException {
        service.deleteArtist(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}