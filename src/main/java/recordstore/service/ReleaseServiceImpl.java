package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ReleaseFormDTO;
import recordstore.entity.Release;
import recordstore.projections.ReleaseProjection;
import recordstore.repository.ReleaseRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;

    private final FileStore fileStore;

    public ReleaseServiceImpl(ReleaseRepository releaseRepository, FileStore fileStore) {
        this.releaseRepository = releaseRepository;
        this.fileStore = fileStore;
    }

    @Override
    public Release getRelease(long id) {
        Release release = releaseRepository.findReleaseById(id);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        return release;
    }

    @Override
    public Page<ReleaseProjection> getAllReleases(Pageable pageable) {
        Page<Long> ids = releaseRepository.findIds(pageable);
        List<ReleaseProjection> result = releaseRepository.findAllByIdIn(ids.getContent());
        return PageableExecutionUtils.getPage(result, pageable, ids::getTotalElements);
    }

    @Override
    public Page<ReleaseProjection> search(String keyword, Pageable pageable) {
        Page<Long> ids = releaseRepository.search(keyword, pageable);
        List<ReleaseProjection> result = releaseRepository.findAllByIdIn(ids.getContent());
        return PageableExecutionUtils.getPage(result, pageable, ids::getTotalElements);
    }

    @Override
    public Release createRelease(ReleaseFormDTO releaseDTO) {
        Release release = new Release();
        release.setCode(releaseDTO.getCode());
        release.setTitle(releaseDTO.getTitle());
        release.setReleaseDate(releaseDTO.getReleaseDate());
        release.setFormat(releaseDTO.getFormat());
        return releaseRepository.save(release);
    }

    @Override
    public Release updateRelease(ReleaseFormDTO releaseDTO, long id) {
        Release release = getRelease(id);
        release.setCode(releaseDTO.getCode());
        release.setTitle(releaseDTO.getTitle());
        release.setReleaseDate(releaseDTO.getReleaseDate());
        release.setFormat(releaseDTO.getFormat());
        return releaseRepository.save(release);
    }

    @Override
    public void deleteRelease(long id) {
        Release release = getRelease(id);
        if (release.getAccounts().isEmpty()) {
            release.removeLabel(release.getLabel());
            //release.setLabel(null);
            releaseRepository.delete(release);
            fileStore.deleteFile(release.getImg());
        }
    }

    @Override
    public Release uploadImage(long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        Release release = getRelease(id);
        String fileName = fileStore.save(file);
        fileStore.deleteFile(release.getImg());
        release.setImg(fileName);
        return releaseRepository.save(release);
    }

    @Override
    public byte[] downloadImage(long id) {
        Release release = getRelease(id);
        return fileStore.download(release.getImg());
    }

    // The handling of associations
//    @Override
//    public Release addLabelToRelease(long releaseId, long labelId) {
//        Release release = getRelease(releaseId);
//        release.setLabel(labelService.getLabel(labelId));
//        return releaseRepository.save(release);
//    }
//
//    @Override
//    public Release addArtistToRelease(long releaseId, long artistId) {
//        Release release = getRelease(releaseId);
//        release.getArtists().add(artistService.getArtist(artistId));
//        return releaseRepository.save(release);
//    }
//
//    @Override
//    public Release addGenreToRelease(long releaseId, long genreId) {
//        Release release = getRelease(releaseId);
//        release.getGenres().add(genreService.getGenre(genreId));
//        return releaseRepository.save(release);
//    }
//
//    @Override
//    public Release removeLabelFromRelease(long releaseId, long labelId) {
//        Release release = getRelease(releaseId);
//        Label label = labelService.getLabel(labelId);
//        if (release.getLabel().equals(label)) {
//            release.setLabel(null);
//        }
//        return releaseRepository.save(release);
//    }
//
//    @Override
//    public Release removeArtistFromRelease(long releaseId, long artistId) {
//        Release release = getRelease(releaseId);
//        release.getArtists().remove(artistService.getArtist(artistId));
//        return releaseRepository.save(release);
//    }
//
//    @Override
//    public Release removeGenreFromRelease(long releaseId, long genreId) {
//        Release release = getRelease(releaseId);
//        release.getGenres().remove(genreService.getGenre(genreId));
//        return releaseRepository.save(release);
//    }
}