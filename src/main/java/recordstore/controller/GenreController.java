package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import recordstore.entity.Genre;
import recordstore.service.GenreService;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController {

    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllGenres(Model model){
        List<Genre> genres = service.getAllGenres();
        model.addAttribute("genres", genres);
        return "client/genres/index";
    }
}