package recordstore.service;

import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Artist;
import recordstore.entity.Genre;
import recordstore.entity.Label;
import recordstore.entity.Release;

import java.io.IOException;
import java.util.List;

public interface ReleaseService {

    Release getRelease(long id);
    void saveRelease(Release release) throws IOException;
    void deleteRelease(long id);

    List<Release> getAllReleases();

    //old methods
    //List<Release> getAllReleasesByGenre(String genre);
    //List<Release> getAllReleasesByArtist(String artist);
}