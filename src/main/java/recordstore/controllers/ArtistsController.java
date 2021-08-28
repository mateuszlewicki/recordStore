package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.DTO.ArtistDTO;
import recordstore.DTO.ArtistSlimDTO;
import recordstore.mapstruct.mappers.MapStructMapper;
import recordstore.service.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistsController {

    private final ArtistService service;
    private final MapStructMapper mapStructMapper;

    public ArtistsController(ArtistService service, MapStructMapper mapStructMapper) {
        this.service = service;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    public Page<ArtistSlimDTO> getAllArtists(Pageable pageable) {
        return service.getAllArtists(pageable).map(mapStructMapper::artistProjectionToArtistSlimDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistInfo(@PathVariable long id){
        ArtistDTO artistDTO =  mapStructMapper.artistToArtistDTO(service.getArtist(id));
        return ResponseEntity.ok(artistDTO);
    }

    @GetMapping("/search")
    public Page<ArtistSlimDTO> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
        return service.search(keyword, pageable).map(mapStructMapper::artistProjectionToArtistSlimDTO);
    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}