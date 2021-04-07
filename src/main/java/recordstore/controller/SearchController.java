package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import recordstore.entity.Artist;
import recordstore.entity.Label;
import recordstore.entity.Release;
import recordstore.service.SearchService;

import java.util.List;

@Controller
@RequestMapping(value = {"/", "/releases", "/labels", "/genres", "/artists", "/account"})
public class SearchController {

    private final SearchService service;

    public SearchController(SearchService service) {
        this.service = service;
    }

    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> showAutocomplete(@RequestParam("term") String term) {
        return service.search(term);
    }

    @GetMapping("/search")
    public String showSearchResult(@RequestParam("search") String search, Model model) {
        Release release = service.getReleaseByTitle(search);
        Label label = service.getLabelByTitle(search);
        Artist artist = service.getArtistByName(search);

        if (release != null) {
            model.addAttribute("release", release);
            return "redirect:/releases/" + release.getId();
        }
        if (label != null) {
            model.addAttribute("label", label);
            return "redirect:/labels/" + label.getId();
        }
        if (artist != null) {
            model.addAttribute("artist", artist);
            return "redirect:/artists/" + artist.getId();
        }
        model.addAttribute("message", "No items found");
        return "client/search";
    }
}