package recordstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import recordstore.projections.LabelProjection;
import recordstore.service.LabelService;

import java.util.List;

@Controller
@RequestMapping("/labels")
public class LabelController {

    private final LabelService service;

    public LabelController(LabelService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllLabels(Model model){
        List<LabelProjection> labels = service.getAllLabelsTitles();
        model.addAttribute("labels", labels);
        return "labels";
    }
}
