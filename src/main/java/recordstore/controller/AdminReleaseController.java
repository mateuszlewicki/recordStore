package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ReleaseDTO;
import recordstore.DTO.TrackDTO;
import recordstore.DTO.YouTubeVideoDTO;
import recordstore.entity.Release;
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

    private final ReleaseMapper releaseMapper;

    private final ReleaseService service;
    private final ArtistService artistService;
    private final LabelService labelService;
    private final GenreService genreService;

    public AdminReleaseController(ReleaseMapper releaseMapper, ReleaseService service,
                                  ArtistService artistService,
                                  LabelService labelService,
                                  GenreService genreService) {
        this.releaseMapper = releaseMapper;
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
        model.addAttribute("release" , releaseDTO);
        getModelAttributes(model);
        return "admin/releases/add";
    }

    @PostMapping("/add")
    public String saveRelease(@Valid @ModelAttribute("release") ReleaseDTO releaseDTO,
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
        ReleaseDTO releaseDTO = releaseMapper.toDTO(service.getRelease(id));
        model.addAttribute("release", releaseDTO);
        getModelAttributes(model);
        return "admin/releases/edit";
    }

    @PostMapping("/edit/{id}")
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

    @PostMapping("/addTrack")
    public String addTrack(@ModelAttribute("release") ReleaseDTO releaseDTO, Model model) {
        releaseDTO.getTracklist().add(new TrackDTO());
        model.addAttribute("release", releaseDTO);
        return "admin/releases/tracks";
    }

    @PostMapping("/removeTrack")
    public String removeTrack(@ModelAttribute("release") ReleaseDTO releaseDTO,
                              @RequestParam("removeIndex") int index, Model model) {
        releaseDTO.getTracklist().remove(releaseDTO.getTracklist().get(index));
        model.addAttribute("release", releaseDTO);
        return "admin/releases/tracks";
    }

    @PostMapping("/addVideo")
    public String addVideo(@ModelAttribute("release") ReleaseDTO releaseDTO, Model model) {
        releaseDTO.getPlaylist().add(new YouTubeVideoDTO());
        model.addAttribute("release", releaseDTO);
        return "admin/releases/video";
    }

    @PostMapping("/removeVideo")
    public String removeVideo(@ModelAttribute("release") ReleaseDTO releaseDTO,
                              @RequestParam("removeIndex") int index, Model model) {
        releaseDTO.getPlaylist().remove(releaseDTO.getPlaylist().get(index));
        model.addAttribute("release", releaseDTO);
        return "admin/releases/video";
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