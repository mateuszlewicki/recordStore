package recordstore.service;

import recordstore.entity.Artist;
import recordstore.entity.Genre;
import recordstore.entity.Label;
import recordstore.entity.Release;

import java.util.List;

public interface ReleaseService {

    Release getRelease(long id);
    void saveRelease(Release release);
    void deleteRelease(long id);

    List<Release> getAllReleases();

    //old methods
    //List<Release> getAllReleasesByGenre(String genre);
    //List<Release> getAllReleasesByArtist(String artist);
}