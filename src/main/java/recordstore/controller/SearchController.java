package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import recordstore.service.ArtistService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = {"/", "/releases", "/labels", "/genres", "/artists"})
public class SearchController {

    private final ArtistService service;

    public SearchController(ArtistService service) {
        this.service = service;
    }

    @RequestMapping(value = "autocomplete")
    @ResponseBody
    public List<String> artistsNamesAutocomplete(HttpServletRequest request) {
        return service.search(request.getParameter("term"));
    }

    @GetMapping("/search")
    public String showSearchResult(@RequestParam("search") String search, Model model) {
        model.addAttribute("artist", service.getArtistByName(search));
        return "client/search/index";
    }
}