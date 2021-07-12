package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recordstore.DTO.GenreDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.GenreService;

@RestController
@RequestMapping("/genres")
public class GenresController {

    private final GenreService service;
    private final MapStructMapper mapStructMapper;

    public GenresController(GenreService service, MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    public Page<GenreDTO> showAllGenres(Pageable pageable){
        return service.getAllGenres(pageable).map(mapStructMapper::genreToGenreDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> showGenreInfo(@PathVariable long id){
        GenreDTO genreDTO = mapStructMapper.genreToGenreDTO(service.getGenre(id));
        return ResponseEntity.ok(genreDTO);
    }
}