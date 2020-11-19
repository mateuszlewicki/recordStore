package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import recordstore.service.ReleaseService;

@Controller
public class SearchController {

    private final ReleaseService service;

    public SearchController(ReleaseService service) {
        this.service = service;
    }

    @RequestMapping(value = "search")
    public String searchArtist(@RequestParam("search") String searchString, Model model){
        //getAllGenres(model);
        if(searchString != null){
            Object searchResult = service.getAllReleasesByArtist(searchString);
            model.addAttribute("releases", searchResult);
        }
        return "search";
    }

}
