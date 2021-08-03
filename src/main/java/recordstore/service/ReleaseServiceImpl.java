package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.*;
import recordstore.entity.*;
import recordstore.repository.ArtistRepository;
import recordstore.repository.GenreRepository;
import recordstore.repository.LabelRepository;
import recordstore.repository.ReleaseRepository;
import recordstore.utils.FileStore;
import javax.persistence.EntityNotFoundException;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;

    private final FileStore fileStore;

    private final LabelRepository labelRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;

    public ReleaseServiceImpl(ReleaseRepository releaseRepository,
                              FileStore fileStore,
                              LabelRepository labelRepository,
                              ArtistRepository artistRepository,
                              GenreRepository genreRepository) {
        this.releaseRepository = releaseRepository;
        this.fileStore = fileStore;
        this.labelRepository = labelRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Release getRelease(long id) {
        if (!releaseRepository.existsById(id)) {
            throw new EntityNotFoundException("Release not found");
        }
        return releaseRepository.getOne(id);
    }

    @Override
    public Page<Release> getAllReleases(Pageable pageable) {
        return releaseRepository.findAll(pageable);
    }

    @Override
    public Page<Release> search(String keyword, Pageable pageable) {
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
        Release release = releaseRepository.getOne(id);
        release.setCode(releaseDTO.getCode());
        release.setTitle(releaseDTO.getTitle());
        release.setReleaseDate(releaseDTO.getReleaseDate());
        release.setFormat(releaseDTO.getFormat());
        return releaseRepository.save(release);
    }

    @Override
    public void deleteRelease(long id) {
        if (!releaseRepository.existsById(id)) {
            throw new EntityNotFoundException("Release not found");
        }
        Release release = releaseRepository.getOne(id);
        if (release.getCollections().isEmpty()) {
            release.setLabel(null);
            //release.removeLabel(release.getLabel());
            releaseRepository.delete(release);
            fileStore.deleteFile(release.getImg());
        }
    }

    @Override
    public Release uploadImage(long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        if (!releaseRepository.existsById(id)) {
            throw new EntityNotFoundException("Release not found");
        }
        Release release = releaseRepository.getOne(id);
        String fileName = fileStore.save(file);
        fileStore.deleteFile(release.getImg());
        release.setImg(fileName);
        return releaseRepository.save(release);
    }

    @Override
    public byte[] downloadImage(long id) {
        if (!releaseRepository.existsById(id)) {
            throw new EntityNotFoundException("Release not found");
        }
        Release release = releaseRepository.getOne(id);
        return fileStore.download(release.getImg());
    }

    @Override
    public Page<Release> getReleasesByGenre(long id, Pageable pageable) {
        return releaseRepository.findReleasesByGenres_id(id, pageable);
    }

    @Override
    public Page<Release> getReleasesByArtist(long id, Pageable pageable) {
        return releaseRepository.findReleasesByArtists_id(id, pageable);
    }

    @Override
    public Page<Release> getReleasesByLabel(long id, Pageable pageable) {
        return releaseRepository.findReleasesByLabel_Id(id, pageable);
    }

    @Override
    public Release addLabelToRelease(long releaseId, long labelId) {
        if (!releaseRepository.existsById(releaseId)) {
            throw new EntityNotFoundException("Release not found");
        }
        if (!labelRepository.existsById(labelId)){
            throw new EntityNotFoundException("Label not found");
        }
        Release release = releaseRepository.getOne(releaseId);
        release.setLabel(labelRepository.getOne(labelId));
        return releaseRepository.save(release);
    }

    @Override
    public Release addArtistToRelease(long releaseId, long artistId) {
        if (!releaseRepository.existsById(releaseId)) {
            throw new EntityNotFoundException("Release not found");
        }
        if (!artistRepository.existsById(artistId)) {
            throw new EntityNotFoundException("Artist not found");
        }
        Release release = releaseRepository.getOne(releaseId);
        release.getArtists().add(artistRepository.getOne(artistId));
        return releaseRepository.save(release);
    }

    @Override
    public Release addGenreToRelease(long releaseId, long genreId) {
        if (!releaseRepository.existsById(releaseId)) {
            throw new EntityNotFoundException("Release not found");
        }
        if (!genreRepository.existsById(genreId)) {
            throw new EntityNotFoundException("Genre not found");
        }
        Release release = releaseRepository.getOne(releaseId);
        release.getGenres().add(genreRepository.getOne(genreId));
        return releaseRepository.save(release);
    }

    @Override
    public Release removeLabelFromRelease(long releaseId, long labelId) {
        if (!releaseRepository.existsById(releaseId)) {
            throw new EntityNotFoundException("Release not found");
        }
        if (!labelRepository.existsById(labelId)){
            throw new EntityNotFoundException("Label not found");
        }
        Release release = releaseRepository.getOne(releaseId);
        Label label = labelRepository.getOne(labelId);
        if (release.getLabel().equals(label)) {
            release.setLabel(null);
        }
        return releaseRepository.save(release);
    }

    @Override
    public Release removeArtistFromRelease(long releaseId, long artistId) {
        if (!releaseRepository.existsById(releaseId)) {
            throw new EntityNotFoundException("Release not found");
        }
        if (!artistRepository.existsById(artistId)) {
            throw new EntityNotFoundException("Artist not found");
        }
        Release release = releaseRepository.getOne(releaseId);
        Artist artist = artistRepository.getOne(artistId);
        release.getArtists().remove(artistRepository.getOne(artistId));
        return releaseRepository.save(release);
    }

    @Override
    public Release removeGenreFromRelease(long releaseId, long genreId) {
        if (!releaseRepository.existsById(releaseId)) {
            throw new EntityNotFoundException("Release not found");
        }
        if (!genreRepository.existsById(genreId)) {
            throw new EntityNotFoundException("Genre not found");
        }
        Release release = releaseRepository.getOne(releaseId);
        release.getGenres().remove(genreRepository.getOne(genreId));
        return releaseRepository.save(release);
    }

    @Override
    public int countReleasesByArtist(Artist artist) {
        return releaseRepository.countAllByArtists(artist);
    }

    @Override
    public int countReleasesByLabel(Label label) {
        return releaseRepository.countAllByLabel(label);
    }

    @Override
    public int countReleasesByGenre(Genre genre) {
        return releaseRepository.countAllByGenres(genre);
    }

    @Override
    public Page<Release> getCollectionByAccount(long id, Pageable pageable) {
        return releaseRepository.findReleasesByCollections_id(id, pageable);
    }

}