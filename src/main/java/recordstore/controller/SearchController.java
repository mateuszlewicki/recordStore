package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import recordstore.service.ArtistService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SearchController {

// 1
//    private final ArtistService service;
//
//    public SearchController(ArtistService service) {
//        this.service = service;
//    }
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