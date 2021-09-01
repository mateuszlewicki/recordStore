package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ReleaseFormDTO;
import recordstore.entity.Label;
import recordstore.entity.Release;
import recordstore.projections.ReleaseProjection;
import recordstore.repository.ReleaseRepository;
import recordstore.utils.FileStore;

import javax.persistence.EntityNotFoundException;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;

    private final FileStore fileStore;

    private LabelService labelService;
    private ArtistService artistService;
    private GenreService genreService;

    public ReleaseServiceImpl(ReleaseRepository releaseRepository,
                              FileStore fileStore) {
        this.releaseRepository = releaseRepository;
        this.fileStore = fileStore;
    }

    @Autowired
    public void setLabelService(LabelService labelService) {
        this.labelService = labelService;
    }

    @Autowired
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
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
        return releaseRepository.findAllBy(pageable);
    }

    @Override
    public Page<ReleaseProjection> search(String keyword, Pageable pageable) {
        return releaseRepository.search(keyword, pageable);
    }

    @Override
    public Page<ReleaseProjection> getReleasesByLabel(long id, Pageable pageable) {
        return releaseRepository.findAllByLabel_Id(id, pageable);
    }

    @Override
    public Page<ReleaseProjection> getReleasesByGenre(long id, Pageable pageable) {
        return releaseRepository.findAllByGenres_id(id, pageable);
    }

    @Override
    public Page<ReleaseProjection> getReleasesByArtist(long id, Pageable pageable) {
        return releaseRepository.findAllByArtists_id(id, pageable);
    }

    @Override
    public Page<ReleaseProjection> getReleasesByAccount(long id, Pageable pageable) {
        return releaseRepository.findAllByAccounts_id(id, pageable);
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
        Release release = releaseRepository.findReleaseById(id);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        release.setCode(releaseDTO.getCode());
        release.setTitle(releaseDTO.getTitle());
        release.setReleaseDate(releaseDTO.getReleaseDate());
        release.setFormat(releaseDTO.getFormat());
        return releaseRepository.save(release);
    }

    @Override
    public void deleteRelease(long id) {
        Release release = releaseRepository.findReleaseById(id);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        if (release.getAccounts().isEmpty()) {
            release.setLabel(null);
            releaseRepository.delete(release);
            fileStore.deleteFile(release.getImg());
        }
    }

    @Override
    public Release uploadImage(long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        Release release = releaseRepository.findReleaseById(id);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        String fileName = fileStore.save(file);
        fileStore.deleteFile(release.getImg());
        release.setImg(fileName);
        return releaseRepository.save(release);
    }

    @Override
    public byte[] downloadImage(long id) {
        Release release = releaseRepository.findReleaseById(id);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        return fileStore.download(release.getImg());
    }

    @Override
    public Release addLabelToRelease(long releaseId, long labelId) {
        Release release = releaseRepository.findReleaseById(releaseId);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        release.setLabel(labelService.getLabel(labelId));
        return releaseRepository.save(release);
    }

    @Override
    public Release addArtistToRelease(long releaseId, long artistId) {
        Release release = releaseRepository.findReleaseById(releaseId);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        release.getArtists().add(artistService.getArtist(artistId));
        return releaseRepository.save(release);
    }

    @Override
    public Release addGenreToRelease(long releaseId, long genreId) {
        Release release = releaseRepository.findReleaseById(releaseId);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        release.getGenres().add(genreService.getGenre(genreId));
        return releaseRepository.save(release);
    }

    @Override
    public Release removeLabelFromRelease(long releaseId, long labelId) {
        Release release = releaseRepository.findReleaseById(releaseId);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        Label label = labelService.getLabel(labelId);
        if (release.getLabel().equals(label)) {
            release.setLabel(null);
        }
        return releaseRepository.save(release);
    }

    @Override
    public Release removeArtistFromRelease(long releaseId, long artistId) {
        Release release = releaseRepository.findReleaseById(releaseId);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        release.getArtists().remove(artistService.getArtist(artistId));
        return releaseRepository.save(release);
    }

    @Override
    public Release removeGenreFromRelease(long releaseId, long genreId) {
        Release release = releaseRepository.findReleaseById(releaseId);
        if (release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        release.getGenres().remove(genreService.getGenre(genreId));
        return releaseRepository.save(release);
    }
}