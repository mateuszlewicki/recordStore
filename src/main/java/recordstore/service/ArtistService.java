package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.DTO.ArtistFormDTO;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;

import java.io.IOException;
import java.util.List;

public interface ArtistService {

    Artist getArtist(long id);
    Page<Artist> getAllArtists(Pageable pageable);

    Artist createArtist(ArtistFormDTO artistDTO) throws IOException;
    Artist updateArtist(ArtistFormDTO artistDTO, long id) throws IOException;
    void deleteArtist(long id) throws IOException;

    // search
    List<String> search(String keyword);
    // find artist by name
    Artist getArtistByName(String name);
    // autocomplete
    List<ArtistProjection> getArtistsNames(String query);
}