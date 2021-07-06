package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.DTO.ArtistDTO;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;

import java.io.IOException;
import java.util.List;

public interface ArtistService {

    Artist createArtist(ArtistDTO artistDTO) throws IOException;
    Artist updateArtist(ArtistDTO artistDTO, long id) throws IOException;
    void deleteArtist(long id) throws IOException;

    Artist getArtist(long id);
    List<ArtistProjection> getArtistsNames(String query);
    Page<Artist> getAllArtists(Pageable pageable);

    List<String> search(String keyword);
    Artist getArtistByName(String name);
}