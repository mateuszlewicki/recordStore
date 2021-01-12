package recordstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ReleaseDTO;
import recordstore.entity.Release;
import recordstore.entity.Track;
import recordstore.enums.Format;
import recordstore.mapper.ReleaseMapper;
import recordstore.service.ArtistService;
import recordstore.service.GenreService;
import recordstore.service.LabelService;
import recordstore.service.ReleaseService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/releases/")
public class AdminReleaseController {

    @Autowired
    private ReleaseMapper releaseMapper;

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
        Page<Release> releases = service.getAllReleases(PageRequest.of(currentPage - 1 ,10 ));
        model.addAttribute("releases", releases);
        getPages(model, releases);
        return "admin/releases/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        ReleaseDTO releaseDTO = new ReleaseDTO();

        releaseDTO.getTracklist().add(new Track());
        releaseDTO.getTracklist().add(new Track());

        model.addAttribute("release" , releaseDTO);
        getModelAttributes(model);
        return "admin/releases/add";
    }

    @PostMapping("/add")
    public String saveRelease(@Valid @ModelAttribute("newRelease") ReleaseDTO releaseDTO,
                             BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            getModelAttributes(model);
            return "admin/releases/add";
        }
        service.saveRelease(releaseMapper.fromDTO(releaseDTO));
        return "redirect:/admin/releases/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model){
        ReleaseDTO release = releaseMapper.toDTO(service.getRelease(id));
        model.addAttribute("release", release);
        getModelAttributes(model);
        return "admin/releases/edit";
    }

    @PostMapping("/update/")
    public String updateRecord(@Valid @ModelAttribute("release") ReleaseDTO releaseDTO,
                               BindingResult result, Model model) throws IOException {
        if(result.hasErrors()){
            getModelAttributes(model);
            return "admin/releases/edit";
        }
        service.saveRelease(releaseMapper.fromDTO(releaseDTO));
        return "redirect:/admin/releases/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id) throws IOException {
    service.deleteRelease(id);
    return "redirect:/admin/releases/";
    }

    @RequestMapping(value = "autocomplete")
    @ResponseBody
    public List<String> releaseTitlesAutocomplete(HttpServletRequest request) {
        return service.search(request.getParameter("term"));
    }

    @GetMapping("/search")
    public String showSearchResult(@RequestParam("search") String search, Model model) {
        model.addAttribute("release", service.getReleaseByTitle(search));
        return "admin/releases/search";
    }

    private void getModelAttributes(Model model) {
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("artists", artistService.getAllArtistsNames());
        model.addAttribute("labels", labelService.getAllLabelsTitles());
        model.addAttribute("formatTypes" , Format.values());
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