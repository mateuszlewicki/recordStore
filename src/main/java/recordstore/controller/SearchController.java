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
        return "search";
    }

//
//    @RequestMapping(value = "search")
//    @ResponseBody
//    public List<String> search(HttpServletRequest request) {
//        return service.search(request.getParameter("term"));
//    }


//    2
//    private final ReleaseService service;
//
//    public SearchController(ReleaseService service) {
//        this.service = service;
//    }
//
//    @RequestMapping(value = "search")
//    public String searchArtist(@RequestParam("search") String searchString, Model model){
//        //getAllGenres(model);
//        if(searchString != null){
//            Object searchResult = service.getAllReleasesByArtist(searchString);
//            model.addAttribute("releases", searchResult);
//        }
//        return "search";
//    }
}