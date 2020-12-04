package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String showAllReleases(Model model, @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Page<Release> releases = service.getAllReleases(PageRequest.of(currentPage - 1 ,10));
        model.addAttribute("releases", releases);
        getPages(model, releases);
        return "admin/releases/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("newRelease" , new Release());
        getModelAttributes(model);
        return "admin/releases/add";
    }

    @PostMapping("/add")
    public String saveRelease(@Valid @ModelAttribute("newRelease") Release release,
                             BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            getModelAttributes(model);
            return "admin/releases/add";
        }
        service.saveRelease(release);
        return "redirect:/admin/releases/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model){
        Release release = service.getRelease(id);
        model.addAttribute("release", release);
        getModelAttributes(model);
        return "admin/releases/edit";
    }

    @PostMapping("/update/")
    public String updateRecord(@Valid @ModelAttribute("release") Release release,
                               BindingResult result, Model model) throws IOException {
        if(result.hasErrors()){
            getModelAttributes(model);
            return "admin/releases/edit";
        }
        service.saveRelease(release);
        return "redirect:/admin/releases/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id) throws IOException {
    service.deleteRelease(id);
    return "redirect:/admin/releases/";
    }

    private Model getModelAttributes(Model model) {
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("artists", artistService.getAllArtists());
        model.addAttribute("labels", labelService.findAllLabels());
        return model;
    }

    private Model getPages(Model model, Page<Release> releases) {
        int pages = releases.getTotalPages();
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return model;
    }
}