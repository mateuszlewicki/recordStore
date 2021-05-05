package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Label;
import recordstore.entity.Release;
import recordstore.service.LabelService;
import recordstore.service.ReleaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/labels")
public class LabelController {

    private final LabelService service;
    private final ReleaseService releaseService;

    public LabelController(LabelService service, ReleaseService releaseService) {
        this.service = service;
        this.releaseService = releaseService;
    }

    @GetMapping
    public String showAllLabels(Model model, @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        Page<Label> labels = service.getAllLabels(PageRequest.of(currentPage - 1, 30, Sort.by("title").ascending()));
        model.addAttribute("labels", labels);
        getPages(model, labels.getTotalPages());
        return "client/labels/index";
    }

    @GetMapping("/{id}")
    public String showLabelInfo(Model model,
                                @PathVariable long id,
                                @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Label label = service.getLabel(id);
        Page<Release> releases = releaseService.getReleasesByLabel(label.getId(), PageRequest.of(currentPage - 1 ,10 , Sort.by("releaseDate").descending()));
        model.addAttribute("label", label);
        model.addAttribute("releases", releases);
        getPages(model, releases.getTotalPages());
        return "client/labels/view";
    }

    private void getPages(Model model, int pages) {
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}