package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ReleaseFormDTO;
import recordstore.entity.Artist;
import recordstore.entity.Genre;
import recordstore.entity.Label;
import recordstore.entity.Release;
import java.io.IOException;

public interface ReleaseService {

    Release getRelease(long id);
    Page<Release> getAllReleases(Pageable pageable);
    Page<Release> search(String keyword, Pageable pageable);

    Release createRelease(ReleaseFormDTO releaseDTO) throws IOException;
    Release updateRelease(ReleaseFormDTO releaseDTO, long id) throws IOException;
    void deleteRelease(long id) throws IOException;

    Release uploadImage(long id, MultipartFile file);
    byte[] downloadImage(long id);

    Page<Release> getReleasesByGenre(long id, Pageable pageable);
    Page<Release> getReleasesByArtist(long id, Pageable pageable);
    Page<Release> getReleasesByLabel(long id, Pageable pageable);

    Release addLabelToRelease(long releaseId, long labelId);
    Release addArtistToRelease(long releaseId, long artistId);
    Release addGenreToRelease(long releaseId, long genreId);

    Release removeLabelFromRelease(long releaseId, long labelId);
    Release removeArtistFromRelease(long releaseId, long artistId);
    Release removeGenreFromRelease(long releaseId, long genreId);

    int countReleasesByArtist(Artist artist);
    int countReleasesByLabel(Label label);
    int countReleasesByGenre(Genre genre);

    Page<Release> getCollectionByAccount(long id, Pageable pageable);
}