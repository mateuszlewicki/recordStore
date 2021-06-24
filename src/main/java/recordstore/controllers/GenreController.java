package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recordstore.DTO.GenreDTO;
import recordstore.mapper.GenreMapper;
import recordstore.service.GenreService;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService service;
    private final GenreMapper mapper;

    public GenreController(GenreService service, GenreMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<GenreDTO> showAllGenres(Pageable pageable){
        return mapper.toDTOs(service.getAllGenres(pageable));
    }

    @GetMapping("/{id}")
    public GenreDTO showGenreInfo(@PathVariable long id){
        return mapper.toDTO(service.getGenre(id));
    }
}