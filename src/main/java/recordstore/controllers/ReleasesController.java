package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ReleaseDTO;
import recordstore.service.ReleaseService;

@RestController
@RequestMapping("/releases")
public class ReleaseController {

    private final ReleaseService service;

    public ReleaseController(ReleaseService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<ReleaseDTO> showAllReleases(Pageable pageable){
        return service.getAllReleases(pageable);
    }

    @GetMapping("/{id}")
    public ReleaseDTO showReleaseInfo(@PathVariable long id){
        return service.getRelease(id);
    }
}