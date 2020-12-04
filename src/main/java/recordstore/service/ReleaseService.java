package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.entity.Release;

import java.io.IOException;

public interface ReleaseService {

    Release getRelease(long id);
    void saveRelease(Release release) throws IOException;
    void deleteRelease(long id) throws IOException;

    Page<Release> getAllReleases(Pageable pageable);
}