package recordstore.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import recordstore.entity.Genre;
import recordstore.entity.Release;
import recordstore.service.GenreService;
import recordstore.service.ReleaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/genres")
public class GenreController {

    private final GenreService service;
    private final ReleaseService releaseService;

    public GenreController(GenreService service, ReleaseService releaseService) {
        this.service = service;
        this.releaseService = releaseService;
    }

    @GetMapping
    public String showAllGenres(Model model){
        List<Genre> genres = service.getAllGenres();
        model.addAttribute("genres", genres);
        return "client/genres/index";
    }

    @GetMapping("/{id}")
    public String showAllReleasesByGenre(Model model,
                                         @PathVariable long id,
                                         @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Genre genre = service.getGenre(id);
        Page<Release> releases = releaseService.getAllReleasesByGenre(genre, PageRequest.of(currentPage - 1 ,10 ));
        model.addAttribute("releases", releases);
        model.addAttribute("genre", genre);
        getPages(model, releases);
        return "client/genres/view";
    }

    private void getPages(Model model, Page<Release> releases) {
        int pages = releases.getTotalPages();
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

}
