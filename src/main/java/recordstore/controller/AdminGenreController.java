package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import recordstore.entity.Genre;
import recordstore.service.GenreService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/genres")
public class AdminGenreController {

    private final GenreService service;

    public AdminGenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllGenres(Model model){
        List<Genre> genres = service.getAllGenres();
        model.addAttribute("genres", genres);
        return "admin/genres/index";
    }

    @GetMapping("/add")
    public String showAddForm(){
        return "admin/genres/add";
    }

    @PostMapping("/add")
    public String saveGenre(@Valid Genre genre, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/genres/add";
        }
        service.saveGenre(genre);
        return "redirect:/admin/genres";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Genre genre = service.getGenre(id);
        model.addAttribute("genre", genre);
        return "admin/genres/edit";
    }

    @PostMapping("/update/")
    public String updateGenre(@Valid Genre genre, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/genres/edit";
        }
        service.saveGenre(genre);
        return "redirect:/admin/genres";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id){
        service.deleteGenre(id);
        return "redirect:/admin/genres";
    }
}