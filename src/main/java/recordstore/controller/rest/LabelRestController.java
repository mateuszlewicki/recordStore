package recordstore.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import recordstore.projections.LabelProjection;
import recordstore.service.LabelService;

import java.util.List;

@RestController
@RequestMapping("label-title")
public class LabelRestController {

    private final LabelService labelService;

    public LabelRestController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping
    public List<LabelProjection> items(@RequestParam(value = "q", required = false) String query) {
        return labelService.getLabelsTitles(query);
    }
}