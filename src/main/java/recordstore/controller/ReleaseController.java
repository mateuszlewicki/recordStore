package recordstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Release;
import recordstore.service.ReleaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/releases")
public class ReleaseController {

    private final ReleaseService service;

    public ReleaseController(ReleaseService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllRecords(Model model){
//        Page<Release> releases = service.getAllReleases();
//        model.addAttribute("releases", releases);
        return "releases";
    }

//    @RequestMapping(value = "autocomplete")
//    @ResponseBody
//    public List<String> releasesTitlesAutocomplete(HttpServletRequest request) {
//        return service.search(request.getParameter("term"));
//    }
//
//    @GetMapping("/search")
//    public String showSearchResult(@RequestParam("search") String search, Model model) {
//        model.addAttribute("releases", service.getReleaseByTitle(search));
//        return "search";
//    }
}