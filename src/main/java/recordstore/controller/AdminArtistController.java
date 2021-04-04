package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ArtistDTO;
import recordstore.entity.Artist;
import recordstore.mapper.ArtistMapper;
import recordstore.service.ArtistService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/artists/")
public class AdminArtistController{

    private final ArtistMapper artistMapper;

    private final ArtistService service;

    public AdminArtistController(ArtistMapper artistMapper, ArtistService service) {
        this.artistMapper = artistMapper;
        this.service = service;
    }

    @GetMapping
    public String showAllArtists(Model model, @RequestParam("page")Optional<Integer> page) {
        int currentPage = page.orElse(1);
        Page<Artist> artists = service.getAllArtists(PageRequest.of(currentPage - 1, 10, Sort.by("name").ascending()));
        model.addAttribute("artists", artists);
        getPages(model, artists);
        return "admin/artists/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("newArtist", new ArtistDTO());
        return "admin/artists/add";
    }

    @PostMapping("/add")
    public String saveArtist(@Valid @ModelAttribute("newArtist") ArtistDTO artistDTO, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/artists/add";
        }
        service.saveArtist(artistMapper.fromDTO(artistDTO));
        return "redirect:/admin/artists/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        if (service.isPresent(id)) {
            ArtistDTO artistDTO = artistMapper.toDTO(service.getArtist(id));
            model.addAttribute("artist", artistDTO);
        } else {
            model.addAttribute("error", "Artist is not found");
        }
        return "admin/artists/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateArtist(@Valid @ModelAttribute("artist") ArtistDTO artistDTO, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/artists/edit";
        }
        service.saveArtist(artistMapper.fromDTO(artistDTO));
        return "redirect:/admin/artists/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) throws IOException {
        if (service.isPresent(id)){
            service.deleteArtist(id);
        }
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