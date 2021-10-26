package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ReleaseFormDTO;
import recordstore.entity.Release;
import recordstore.projections.ReleaseProjection;

public interface ReleaseService {

    Release getRelease(long id);

    Page<ReleaseProjection> getAllReleases(Pageable pageable);
    Page<ReleaseProjection> search(String keyword, Pageable pageable);

//    Page<ReleaseProjection> getReleasesByLabel(long id, Pageable pageable);
//    Page<ReleaseProjection> getReleasesByGenre(long id, Pageable pageable);
//    Page<ReleaseProjection> getReleasesByArtist(long id, Pageable pageable);
//    Page<ReleaseProjection> getReleasesByAccount(long id, Pageable pageable);

    Release createRelease(ReleaseFormDTO releaseDTO);
    Release updateRelease(ReleaseFormDTO releaseDTO, long id);
    void deleteRelease(long id);

    Release uploadImage(long id, MultipartFile file);
    byte[] downloadImage(long id);

    // The handling of associations
//    Release addLabelToRelease(long releaseId, long labelId);
//    Release addArtistToRelease(long releaseId, long artistId);
//    Release addGenreToRelease(long releaseId, long genreId);
//
//    Release removeLabelFromRelease(long releaseId, long labelId);
//    Release removeArtistFromRelease(long releaseId, long artistId);
//    Release removeGenreFromRelease(long releaseId, long genreId);
}