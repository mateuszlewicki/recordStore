package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ReleaseDTO;
import recordstore.DTO.ReleaseFormDTO;
import recordstore.entity.Release;
import recordstore.repository.ReleaseRepository;
import recordstore.service.s3.FileStorage;

import javax.persistence.EntityNotFoundException;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;
    private final FileStorage fileStore;

    public ReleaseServiceImpl(ReleaseRepository releaseRepository, FileStorage fileStore) {
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
    public Page<ReleaseDTO> getAllReleases(Pageable pageable) {
        return releaseRepository.findAllBy(pageable);
    }

    @Override
    public Page<ReleaseDTO> search(String keyword, Pageable pageable) {
        return releaseRepository.search(keyword, pageable);
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