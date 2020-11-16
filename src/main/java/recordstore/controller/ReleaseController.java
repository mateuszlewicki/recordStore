package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Release;
import recordstore.service.ReleaseService;

import java.util.List;

@Controller
@RequestMapping("/")
public class ReleaseController {

    private final ReleaseService service;

    private List<Release> releases;

    public ReleaseController(ReleaseService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllRecords(Model model){
        releases = service.getAllReleases();
        getAllGenres(model);
        model.addAttribute("releases", releases);
        return "index";
    }

    @GetMapping("{genre}")
    public String getRecordsByGenre(@PathVariable("genre") String genre, Model model){
        releases = service.getAllReleasesByGenre(genre);
        getAllGenres(model);
        model.addAttribute("releases", releases);
        return "index";
    }

    private void getAllGenres(Model model){
        List<String> genres;
        genres = service.getAllGenres();
        model.addAttribute("genres", genres);
    }

    @RequestMapping(value = "search")
    public String searchArtist(@RequestParam("search") String searchString, Model model){
        getAllGenres(model);
        if(searchString != null){
            Object searchResult = service.getAllReleasesByArtist(searchString);
            model.addAttribute("releases", searchResult);
        }
        return "index";
    }
}