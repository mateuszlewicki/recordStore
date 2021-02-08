package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import recordstore.entity.Label;
import recordstore.service.LabelService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/labels")
public class LabelController {

    private final LabelService service;

    public LabelController(LabelService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllLabels(Model model, @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        Page<Label> labels = service.getAllLabels(PageRequest.of(currentPage - 1, 10));
        model.addAttribute("labels", labels);
        getPages(model, labels);
        return "client/labels/index";
    }

    @GetMapping("/{id}")
    public String showLabelInfo(@PathVariable long id, Model model){
        if (service.isPresent(id)) {
            model.addAttribute("label", service.getLabel(id));
        } else {
            model.addAttribute("error", "Label not found");
        }
        return "client/labels/view";
    }

    private void getPages(Model model, Page<Label> labels) {
        int pages = labels.getTotalPages();
        if (pages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}