package recordstore.service;

import recordstore.entity.Release;

import java.util.List;

public interface ReleaseService {

    Release getRelease(long id);
    void saveRelease(Release release);
    void deleteRelease(long id);

    List<String> getAllGenres();
    List<Release> getAllReleasesByGenre(String genre);
    List<Release> getAllReleases();
    List<Release> getAllReleasesByArtist(String artist);
}
