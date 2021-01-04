package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;
import recordstore.service.ArtistService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/artists")
public class AdminArtistController {

    private final ArtistService service;

    public AdminArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllArtists(Model model, @RequestParam("page")Optional<Integer> page) {
        int currentPage = page.orElse(1);
        Page<Artist> artists = service.getAllArtists(PageRequest.of(currentPage - 1, 10));
        model.addAttribute("artists", artists);
        getPages(model, artists);
        return "admin/artists/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("newArtist", new Artist());
        return "admin/artists/add";
    }

    @PostMapping("/add")
    public String saveArtist(@Valid @ModelAttribute("newArtist") Artist artist, BindingResult result) throws IOException {
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
    public String updateArtist(@Valid @ModelAttribute("artist") Artist artist, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/artists/edit";
        }
        service.saveArtist(artist);
        return "redirect:/admin/artists/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id) throws IOException {
        service.deleteArtist(id);
        return "redirect:/admin/artists/";
    }

    @RequestMapping(value = "autocomplete")
    @ResponseBody
    public List<String> artistsNamesAutocomplete(HttpServletRequest request) {
        return service.search(request.getParameter("term"));
    }

    @GetMapping("/search")
    public String showSearchResult(@RequestParam("search") String search, Model model) {
        model.addAttribute("artist", service.getArtistByName(search));
        return "admin/artists/search";
    }

    private void getPages(Model model, Page<Artist> artists) {
        int pages = artists.getTotalPages();
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}