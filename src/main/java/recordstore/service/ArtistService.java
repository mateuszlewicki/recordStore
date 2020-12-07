package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;

import java.io.IOException;
import java.util.List;

public interface ArtistService {

    void saveArtist(Artist artist) throws IOException;
    void deleteArtist(long id) throws IOException;

    Artist getArtist(long id);
    List<ArtistProjection> getAllArtistsNames();
    Page<Artist> getAllArtists(Pageable pageable);

    List<String> search(String keyword);
    Artist getArtistByName(String query);
}