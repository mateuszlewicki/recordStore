package recordstore.controller.admin;

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
@RequestMapping(value = {"/admin", "/admin/releases", "/admin/labels", "/admin/genres", "/admin/artists", "/admin/accounts"})
public class AdminSearchController {

    private final SearchService service;

    public AdminSearchController(SearchService service) {
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
            return "redirect:/admin/releases/" + release.getId();
        }
        if (label != null) {
            return "redirect:/admin/labels/" + label.getId();
        }
        if (artist != null) {
            return "redirect:/admin/artists/" + artist.getId();
        }
        model.addAttribute("message", "No items found");
        return "/admin/search";
    }
}