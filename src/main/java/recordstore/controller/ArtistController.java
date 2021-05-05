package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Artist;
import recordstore.entity.Release;
import recordstore.service.ArtistService;
import recordstore.service.ReleaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService service;
    private final ReleaseService releaseService;

    public ArtistController(ArtistService service, ReleaseService releaseService) {
        this.service = service;
        this.releaseService = releaseService;
    }

    @GetMapping
    public String showAllArtists(Model model, @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        Page<Artist> artists = service.getAllArtists(PageRequest.of(currentPage - 1, 30, Sort.by("name").ascending()));
        model.addAttribute("artists", artists);
        getPages(model, artists.getTotalPages());
        return "client/artists/index";
    }

    @GetMapping("/{id}")
    public String showArtistInfo(Model model,
                                 @PathVariable long id,
                                 @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Artist artist = service.getArtist(id);
        Page<Release> releases = releaseService.getReleasesByArtist(artist.getId(), PageRequest.of(currentPage - 1 ,10 , Sort.by("releaseDate").descending()));
        model.addAttribute("artist", artist);
        model.addAttribute("releases", releases);
        getPages(model,releases.getTotalPages());
        return "client/artists/view";
    }

    private void getPages(Model model, int pages) {
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}