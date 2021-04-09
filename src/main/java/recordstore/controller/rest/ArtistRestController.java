package recordstore.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import recordstore.projections.ArtistProjection;
import recordstore.service.ArtistService;

import java.util.List;

@RestController
@RequestMapping("artist-name")
public class ArtistRestController {

    private final ArtistService artistService;

    public ArtistRestController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<ArtistProjection> items(@RequestParam(value = "q", required = false) String query) {
        return artistService.getArtistsNames(query);
    }
}