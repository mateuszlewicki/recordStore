package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ArtistFormDTO;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;
import recordstore.repository.ArtistRepository;
import recordstore.repository.ReleaseRepository;
import recordstore.utils.FileStore;
import javax.persistence.EntityNotFoundException;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository repository;
    private final FileStore fileStore;

    private ReleaseRepository releaseRepository;

    public ArtistServiceImpl(ArtistRepository repository, FileStore fileStore) {
        this.repository = repository;
        this.fileStore = fileStore;
    }

    @Autowired
    public void setReleaseRepository(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    @Override
    public Artist getArtist(long id) {
        Artist artist = repository.findArtistById(id);
        if (artist == null) {
            throw new EntityNotFoundException("Artist not found");
        }
        return artist;
    }

    @Override
    public Page<ArtistProjection> getAllArtists(Pageable pageable) {
        return repository.findAllBy(pageable);
    }

    @Override
    public Page<ArtistProjection> search(String keyword, Pageable pageable) {
        return repository.search(keyword, pageable);
    }

    @Override
    public Artist createArtist(ArtistFormDTO artistDTO) {
        Artist artist = new Artist();
        artist.setName(artistDTO.getName());
        artist.setCountry(artistDTO.getCountry());
        artist.setDescription(artistDTO.getDescription());
        return repository.save(artist);
    }

    @Override
    public Artist updateArtist(ArtistFormDTO artistDTO, long id) {
        Artist artist = repository.findArtistById(id);
        if (artist == null) {
            throw new EntityNotFoundException("Artist not found");
        }
        artist.setName(artistDTO.getName());
        artist.setCountry(artistDTO.getCountry());
        artist.setDescription(artistDTO.getDescription());
        return repository.save(artist);
    }

    @Override
    public void deleteArtist(long id) {
        Artist artist = repository.findArtistById(id);
        if (artist == null) {
            throw new EntityNotFoundException("Artist not found");
        }
        if (!releaseRepository.existsReleasesByArtists_id(id)) {
            repository.deleteById(id);
            fileStore.deleteFile(artist.getImg());
        }
    }

    @Override
    public Artist uploadImage(long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        Artist artist = repository.findArtistById(id);
        if (artist == null) {
            throw new EntityNotFoundException("Artist not found");
        }
        String fileName = fileStore.save(file);
        fileStore.deleteFile(artist.getImg());
        artist.setImg(fileName);
        return repository.save(artist);
    }

    @Override
    public byte[] downloadImage(long id) {
        Artist artist = repository.findArtistById(id);
        if (artist == null) {
            throw new EntityNotFoundException("Artist not found");
        }
        return fileStore.download(artist.getImg());
    }
}