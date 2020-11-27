package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;
import recordstore.service.ArtistService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/artists")
public class AdminArtistController {

    private final ArtistService service;

    public AdminArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllArtists(Model model) {
        List<ArtistProjection> artists = service.getAllArtists();
        model.addAttribute("artists", artists);
        return "admin/artists/index";
    }

    @GetMapping("/add")
    public String showAddForm(){
        return "admin/artists/add";
    }

    @PostMapping("/add")
    public String saveArtist(@Valid Artist artist, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/artists/add";
        }
        service.saveArtist(artist);
        return "redirect:/admin/artists/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Artist artist = service.getArtist(id);
        model.addAttribute("artist", artist);
        return "admin/artists/edit";
    }

    @PostMapping("/update")
    public String updateArtist(@Valid Artist artist, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/artists/edit";
        }
        service.saveArtist(artist);
        return "redirect:/admin/artists/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id){
        service.deleteArtist(id);
        return "redirect:/admin/artists/";
    }
}