package recordstore.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import recordstore.projections.GenreProjection;
import recordstore.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("genre-title")
public class GenreRestController {

    private final GenreService genreService;

    public GenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreProjection> items(@RequestParam(value = "q", required = false) String query) {
        return genreService.getGenresTitles(query);
    }
}