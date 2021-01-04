package recordstore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;
import recordstore.service.LabelService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/labels")
public class AdminLabelController {

    private final LabelService service;

    public AdminLabelController(LabelService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllLabels(Model model, @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Page<Label> labels = service.getAllLabels(PageRequest.of(currentPage - 1, 10));
        model.addAttribute("labels", labels);
        getPages(model, labels);
        return "admin/labels/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("newLabel", new Label());
        return "admin/labels/add";
    }

    @PostMapping("/add")
    public String saveLabel(@Valid @ModelAttribute("newLabel") Label label, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/labels/add";
        }
        service.saveLabel(label);
        return "redirect:/admin/labels/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm (@PathVariable long id, Model model) {
        Label label = service.getLabel(id);
        model.addAttribute("label", label);
        return "admin/labels/edit";
    }

    @PostMapping("/update")
    public String updateLabel(@Valid @ModelAttribute("label") Label label, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/labels/edit";
        }
        service.saveLabel(label);
        return "redirect:/admin/labels/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id) throws IOException {
        service.deleteLabel(id);
        return "redirect:/admin/labels/";
    }

    @RequestMapping(value = "autocomplete")
    @ResponseBody
    public List<String> labelsTitleAutocomplete(HttpServletRequest request) {
        return service.search(request.getParameter("term"));
    }

    @GetMapping("/search")
    public String showSearchResult(@RequestParam("search") String search, Model model) {
        model.addAttribute("label", service.getLabelByTitle(search));
        return "admin/labels/search";
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