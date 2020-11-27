package recordstore.service;

import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;

import java.util.List;

public interface ArtistService {

    void saveArtist(Artist artist);
    void deleteArtist(long id);

    Artist getArtist(long id);
    List<ArtistProjection> getAllArtists();

//    old methods
//    List<String> search(String keyword);
}