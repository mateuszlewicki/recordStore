package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recordstore.DTO.LabelDTO;
import recordstore.mapper.LabelMapper;
import recordstore.service.LabelService;

@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelService service;
    private final LabelMapper mapper;

    public LabelController(LabelService service, LabelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping()
    public Page<LabelDTO> showAllLabels(Pageable pageable) {
        return mapper.toDTOs(service.getAllLabels(pageable));
    }

    @GetMapping("/{id}")
    public LabelDTO showLabelInfo(@PathVariable long id){
        return mapper.toDTO(service.getLabel(id));
    }
}