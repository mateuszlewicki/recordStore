package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ArtistDTO;
import recordstore.DTO.ArtistFormDTO;
import recordstore.entity.Artist;
import recordstore.repository.ArtistRepository;
import recordstore.service.s3.FileStorage;

import javax.persistence.EntityNotFoundException;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository repository;
    private final FileStorage fileStore;

    public ArtistServiceImpl(ArtistRepository repository, FileStorage fileStore) {
        this.repository = repository;
        this.fileStore = fileStore;
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
    public Page<ArtistDTO> getAllArtists(Pageable pageable) {
        return repository.findAllBy(pageable);
    }

    @Override
    public Page<ArtistDTO> search(String keyword, Pageable pageable) {
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
        Artist artist = getArtist(id);
        artist.setName(artistDTO.getName());
        artist.setCountry(artistDTO.getCountry());
        artist.setDescription(artistDTO.getDescription());
        return repository.save(artist);
    }

    @Override
    public void deleteArtist(long id) {
        Artist artist = getArtist(id);
        if (artist.getReleases().isEmpty()) {
            repository.deleteById(id);
            fileStore.deleteFile(artist.getImg());
        }
    }

    @Override
    public Artist uploadImage(long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        Artist artist = getArtist(id);
        String fileName = fileStore.save(file);
        fileStore.deleteFile(artist.getImg());
        artist.setImg(fileName);
        return repository.save(artist);
    }

    @Override
    public byte[] downloadImage(long id) {
        Artist artist = getArtist(id);
        return fileStore.download(artist.getImg());
    }
}