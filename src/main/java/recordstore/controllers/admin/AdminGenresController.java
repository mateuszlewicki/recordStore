package recordstore.controllers.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.GenreDTO;
import recordstore.mapper.GenreMapper;
import recordstore.service.GenreService;
import javax.validation.Valid;

@RestController
@RequestMapping("/admin/genres")
public class AdminGenresController {

    private final GenreService service;
    private final GenreMapper mapper;

    public AdminGenresController(GenreService service, GenreMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping()
    public Page<GenreDTO> showAllGenres(Pageable pageable){
        return mapper.toDTOs(service.getAllGenres(pageable));
    }

    @PostMapping()
    public ResponseEntity<String> addGenre(@Valid @RequestBody GenreDTO genreDTO) {
        service.createGenre(genreDTO);
        return ResponseEntity.ok("Genre has been created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGenre(@Valid @RequestBody GenreDTO genreDTO, @PathVariable long id) {
        service.updateGenre(genreDTO, id);
        return ResponseEntity.ok("Genre has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteGenre(@PathVariable long id) {
        service.deleteGenre(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}