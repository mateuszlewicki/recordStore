package recordstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;
import recordstore.service.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistsController {

    private final ArtistService service;

    public ArtistsController(ArtistService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<ArtistProjection> getAllArtists(Pageable pageable) {
        return service.getAllArtists(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistInfo(@PathVariable long id){
        Artist artist = service.getArtist(id);
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/search")
    public Page<ArtistProjection> getSearchResults(@RequestParam("keyword") String keyword, Pageable pageable) {
        return service.search(keyword, pageable);
    }

//    @GetMapping("/{id}/releases")
//    public Page<ReleaseProjection> getAllReleasesByArtist(@PathVariable long id, Pageable pageable){
//        return service.getReleasesByArtist(id, pageable);
//    }

    @GetMapping("/{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") long id) {
        return service.downloadImage(id);
    }
}