package recordstore.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.LabelDTO;
import recordstore.entity.Label;
import recordstore.mapper.LabelMapper;
import recordstore.service.LabelService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/labels")
public class AdminLabelController {

    private final LabelMapper labelMapper;

    private final LabelService service;

    public AdminLabelController(LabelMapper labelMapper, LabelService service) {
        this.labelMapper = labelMapper;
        this.service = service;
    }

    @GetMapping
    public String showAllLabels(Model model, @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        Page<Label> labels = service.getAllLabels(PageRequest.of(currentPage - 1, 10, Sort.by("title").ascending()));
        model.addAttribute("labels", labels);
        getPages(model, labels);
        return "admin/labels/index";
    }

    @GetMapping("/{id}")
    public String showLabelInfo(@PathVariable long id, Model model) {
        if (service.isPresent(id)) {
            model.addAttribute("label", service.getLabel(id));
        } else {
            model.addAttribute("error", "Label not found");
        }
        return "/admin/labels/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("newLabel", new LabelDTO());
        return "admin/labels/add";
    }

    @PostMapping("/add")
    public String saveLabel(@Valid @ModelAttribute("newLabel") LabelDTO labelDTO, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/labels/add";
        }
        service.saveLabel(labelMapper.fromDTO(labelDTO));
        return "redirect:/admin/labels/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm (@PathVariable long id, Model model) {
        if (service.isPresent(id)) {
            LabelDTO labelDTO = labelMapper.toDTO(service.getLabel(id));
            model.addAttribute("label", labelDTO);
        } else {
            model.addAttribute("error", "Label is not found");
        }
        return "admin/labels/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateLabel(@Valid @ModelAttribute("label") LabelDTO labelDTO, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/labels/edit";
        }
        service.saveLabel(labelMapper.fromDTO(labelDTO));
        return "redirect:/admin/labels/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) throws IOException {
        if (service.isPresent(id)){
            service.deleteLabel(id);
        }
        return "redirect:/admin/labels/";
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