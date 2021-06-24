package recordstore.controller.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Genre;
import recordstore.service.GenreService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/genres/")
public class AdminGenreController {

    private final GenreService service;

    public AdminGenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllGenres(Model model , Pageable pageable){
        model.addAttribute("genres", service.getAllGenres(pageable));
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
        return "redirect:/admin/genres/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("genre", service.getGenre(id));
        return "admin/genres/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateGenre(@Valid Genre genre, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/genres/edit";
        }
        service.saveGenre(genre);
        return "redirect:/admin/genres/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        service.deleteGenre(id);
        return "redirect:/admin/genres/";
    }
}