package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recordstore.DTO.GenreFormDTO;
import recordstore.entity.Genre;
import recordstore.service.GenreService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin/genres")
public class AdminGenresController {

    private final GenreService service;

    public AdminGenresController(GenreService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<Genre> getAllGenres(Pageable pageable){
        return service.getAllGenres(pageable);
    }

    @PostMapping()
    public ResponseEntity<Genre> addGenre(@Valid @RequestBody GenreFormDTO genreDTO) {
        Genre created = service.createGenre(genreDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@Valid @RequestBody GenreFormDTO genreDTO, @PathVariable long id) {
        Genre updated = service.updateGenre(genreDTO, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGenre(@PathVariable long id) {
        service.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}