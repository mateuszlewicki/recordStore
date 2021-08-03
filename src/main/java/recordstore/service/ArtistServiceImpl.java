package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ArtistFormDTO;
import recordstore.entity.Artist;
import recordstore.repository.ArtistRepository;
import recordstore.utils.FileStore;
import javax.persistence.EntityNotFoundException;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository repository;
    private final FileStore fileStore;
    private final ReleaseService releaseService;

    public ArtistServiceImpl(ArtistRepository repository, FileStore fileStore, ReleaseService releaseService) {
        this.repository = repository;
        this.fileStore = fileStore;
        this.releaseService = releaseService;
    }

    @Override
    public Artist getArtist(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Artist not found");
        }
        return repository.getOne(id);
    }

    @Override
    public Page<Artist> getAllArtists(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Artist> search(String keyword, Pageable pageable) {
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
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Artist not found");
        }
        Artist artist = repository.getOne(id);
        artist.setName(artistDTO.getName());
        artist.setCountry(artistDTO.getCountry());
        artist.setDescription(artistDTO.getDescription());
        return repository.save(artist);
    }

    @Override
    public void deleteArtist(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Artist not found");
        }
        Artist artist = repository.getOne(id);
        if (releaseService.countReleasesByArtist(artist) == 0) {
            repository.deleteById(id);
            fileStore.deleteFile(artist.getImg());
        }
    }

    @Override
    public Artist uploadImage(long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Artist not found");
        }
        Artist artist = repository.getOne(id);
        String fileName = fileStore.save(file);
        fileStore.deleteFile(artist.getImg());
        artist.setImg(fileName);
        return repository.save(artist);
    }

    @Override
    public byte[] downloadImage(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        Artist artist = repository.getOne(id);
        return fileStore.download(artist.getImg());
    }
}