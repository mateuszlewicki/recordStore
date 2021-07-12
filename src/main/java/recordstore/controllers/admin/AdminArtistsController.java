package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recordstore.DTO.ArtistDTO;
import recordstore.DTO.ArtistFormDTO;
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

    @GetMapping
    public Page<ArtistDTO> showAllArtists(Pageable pageable) {
        return service.getAllArtists(pageable).map(mapStructMapper::artistToArtistDTO);
    }

    @PostMapping()
    public ResponseEntity<ArtistDTO> addArtist(@Valid ArtistFormDTO artistDTO) throws IOException {
        ArtistDTO createdArtistDTO = mapStructMapper.artistToArtistDTO(service.createArtist(artistDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdArtistDTO.getId()).toUri();
        return ResponseEntity.ok(createdArtistDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(@Valid ArtistFormDTO artistDTO, @PathVariable long id) throws IOException {
        ArtistDTO updatedArtistDTO = mapStructMapper.artistToArtistDTO(service.updateArtist(artistDTO, id));
        return ResponseEntity.ok(updatedArtistDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteArtist(@PathVariable long id) throws IOException {
        service.deleteArtist(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}