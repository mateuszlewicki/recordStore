package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Artist;
import recordstore.entity.Genre;
import recordstore.entity.Label;
import recordstore.entity.Release;
import recordstore.projections.ArtistProjection;
import recordstore.projections.LabelProjection;
import recordstore.service.ArtistService;
import recordstore.service.GenreService;
import recordstore.service.LabelService;
import recordstore.service.ReleaseService;

import javax.sound.midi.Soundbank;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

@Controller
@RequestMapping("/admin/releases/")
public class AdminReleaseController {

    private final ReleaseService service;
    private final ArtistService artistService;
    private final LabelService labelService;
    private final GenreService genreService;

    public AdminReleaseController(ReleaseService service,
                                  ArtistService artistService,
                                  LabelService labelService,
                                  GenreService genreService) {
        this.service = service;
        this.artistService = artistService;
        this.labelService = labelService;
        this.genreService = genreService;
    }

    @GetMapping
    public String showAllReleases(Model model){
        List<Release> releases = service.getAllReleases();
        model.addAttribute("releases", releases);
        return "admin/releases/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("newRelease" , new Release());
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("artists", artistService.getAllArtists());
        model.addAttribute("labels", labelService.findAllLabels());
        return "admin/releases/add";
    }

    @PostMapping("/add")
    public String saveRelease(@Valid @ModelAttribute("newRelease") Release release,
                             BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/releases/add";
        }
        service.saveRelease(release);
        return "redirect:/admin/releases/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model){
        Release release = service.getRelease(id);
        model.addAttribute("release", release);
        return "admin/releases/edit";
    }

//    @PostMapping("/update/")
//    public String updateRecord(@Valid Release release, BindingResult result){
//        if(result.hasErrors()){
//            return "admin/releases/edit";
//        }
//        service.saveRelease(release);
//        return "redirect:/admin/releases/";
//    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id){
    service.deleteRelease(id);
    return "redirect:/admin/releases/";
    }
}