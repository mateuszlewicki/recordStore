package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ReleaseDTO;
import recordstore.DTO.ReleaseFormDTO;
import recordstore.entity.Release;

public interface ReleaseService {

    Release getRelease(long id);

    Page<ReleaseDTO> getAllReleases(Pageable pageable);
    Page<ReleaseDTO> search(String keyword, Pageable pageable);

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