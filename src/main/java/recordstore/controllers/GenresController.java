package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recordstore.entity.Genre;
import recordstore.service.GenreService;

@RestController
@RequestMapping("/genres")
public class GenresController {

    private final GenreService service;

    public GenresController(GenreService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<Genre> getAllGenres(Pageable pageable){
        return service.getAllGenres(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreInfo(@PathVariable long id){
        Genre genre = service.getGenre(id);
        return ResponseEntity.ok(genre);
    }

//    @GetMapping("/{id}/releases")
//    public Page<ReleaseProjection> getAllReleasesByGenre(@PathVariable long id, Pageable pageable){
//        return releaseService.getReleasesByGenre(id, pageable);
//    }
}