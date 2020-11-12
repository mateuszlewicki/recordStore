package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import recordstore.service.RecordService;
import recordstore.entity.Record;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class RecordController {

    private final RecordService service;

    private List<Record> records;

    public RecordController(RecordService service) {
        this.service = service;
    }

    @GetMapping
    public String getRecords(Model model){
        records = service.getAllRecords();
        getAllGenres(model);
        model.addAttribute("records", records);
        return "index";
    }

    @GetMapping("{genre}")
    public String getRecordsByGenre(@PathVariable("genre") String genre, Model model){
        records = service.getAllRecordsByGenre(genre);
        getAllGenres(model);
        model.addAttribute("records", records);
        return "index";
    }

    private void getAllGenres(Model model){
        List<String> genres;
        genres = service.getAllGenres();
        model.addAttribute("genres", genres);
    }

    @RequestMapping(value = "search")
    public String searchArtist(@RequestParam("search") String searchString, Model model){
        getAllGenres(model);
        if(searchString != null){
            Object searchResult = service.getAllRecordsByArtist(searchString);
            model.addAttribute("records", searchResult);
        }
        return "index";
    }
}