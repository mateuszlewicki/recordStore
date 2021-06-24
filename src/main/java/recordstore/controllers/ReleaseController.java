package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ReleaseDTO;
import recordstore.mapper.ReleaseMapper;
import recordstore.service.ReleaseService;

@RestController
@RequestMapping("/releases")
public class ReleaseController {

    private final ReleaseService service;
    private final ReleaseMapper mapper;

    public ReleaseController(ReleaseService service, ReleaseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<ReleaseDTO> showAllReleases(Pageable pageable){
        return mapper.toDTOs(service.getAllReleases(pageable));
    }

    @GetMapping("/{id}")
    public ReleaseDTO showReleaseInfo(@PathVariable long id){
        return mapper.toDTO(service.getRelease(id));
    }
}