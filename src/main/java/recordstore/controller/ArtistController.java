package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import recordstore.entity.Artist;
import recordstore.service.ArtistService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService service;

    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllArtists(Model model, @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        Page<Artist> artists = service.getAllArtists(PageRequest.of(currentPage - 1, 10));
        model.addAttribute("artists", artists);
        getPages(model, artists);
        return "client/artists/index";
    }

    @GetMapping("/{id}")
    public String showArtistInfo(@PathVariable long id, Model model){
        model.addAttribute("artist", service.getArtist(id));
        return "client/artists/view";
    }

    private void getPages(Model model, Page<Artist> artists) {
        int pages = artists.getTotalPages();
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
