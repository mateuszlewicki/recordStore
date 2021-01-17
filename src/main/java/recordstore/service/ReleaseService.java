package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.entity.Genre;
import recordstore.entity.Release;

import java.io.IOException;
import java.util.List;

public interface ReleaseService {

    Release getRelease(long id);
    void saveRelease(Release release) throws IOException;
    void deleteRelease(long id) throws IOException;

    Page<Release> getAllReleases(Pageable pageable);
    Page<Release> getAllReleasesByGenre(Genre genre, Pageable pageable);

    List<String> search(String keyword);
    Release getReleaseByTitle(String title);
}