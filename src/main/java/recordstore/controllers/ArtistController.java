package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ArtistDTO;
import recordstore.mapper.ArtistMapper;
import recordstore.service.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService service;
    private final ArtistMapper mapper;

    public ArtistController(ArtistService service, ArtistMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<ArtistDTO> showAllArtists(Pageable pageable) {
        return mapper.toDTOs(service.getAllArtists(pageable));
    }

    @GetMapping("/{id}")
    public ArtistDTO showArtistInfo(@PathVariable long id){
        return mapper.toDTO(service.getArtist(id));
    }
}