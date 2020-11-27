package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;
import recordstore.service.LabelService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/labels")
public class AdminLabelController {

    private final LabelService service;

    public AdminLabelController(LabelService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllLabels(Model model){
        List<LabelProjection> labels = service.findAllLabels();
        model.addAttribute("labels", labels);
        return "admin/labels/index";
    }

    @GetMapping("/add")
    public String showAddForm(){
        return "admin/labels/add";
    }

    @PostMapping("/add")
    public String saveLabel(@Valid Label label, BindingResult result) {
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
    public String updateLabel(@Valid Label label, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/labels/edit";
        }
        service.saveLabel(label);
        return "redirect:/admin/labels/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id){
        service.deleteLabel(id);
        return "redirect:/admin/labels/";
    }
}