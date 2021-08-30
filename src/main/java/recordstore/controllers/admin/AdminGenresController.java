package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recordstore.DTO.GenreDTO;
import recordstore.DTO.GenreFormDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.GenreService;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin/genres")
public class AdminGenresController {

    private final GenreService service;
    private final MapStructMapper mapStructMapper;

    public AdminGenresController(GenreService service,  MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    public Page<GenreDTO> getAllGenres(Pageable pageable){
        return service.getAllGenres(pageable).map(mapStructMapper::genreToGenreDTO);
    }

    @PostMapping()
    public ResponseEntity<GenreDTO> addGenre(@Valid @RequestBody GenreFormDTO genreDTO) {
        GenreDTO createdGenreDTO = mapStructMapper.genreToGenreDTO(service.createGenre(genreDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdGenreDTO.getId()).toUri();
        return ResponseEntity.ok(createdGenreDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@Valid @RequestBody GenreFormDTO genreDTO, @PathVariable long id) {
        GenreDTO updatedGenreDTO = mapStructMapper.genreToGenreDTO(service.updateGenre(id, genreDTO));
        return ResponseEntity.ok(updatedGenreDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGenre(@PathVariable long id) {
        service.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}