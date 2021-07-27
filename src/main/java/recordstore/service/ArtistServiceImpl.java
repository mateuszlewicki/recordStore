package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import recordstore.DTO.ArtistFormDTO;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;
import recordstore.repository.ArtistRepository;
import recordstore.utils.FileService;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository repository;
    private final FileService fileService;

    public ArtistServiceImpl(ArtistRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
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
    public Artist createArtist(ArtistFormDTO artistDTO) throws IOException {
        Artist artist = new Artist();
        artist.setName(artistDTO.getName());
        artist.setCountry(artistDTO.getCountry());
        artist.setDescription(artistDTO.getDescription());
        if (!artistDTO.getData().isEmpty()) {
            artist.setImg(fileService.saveFile(artistDTO.getData()));
        }
        return repository.save(artist);
    }

    @Override
    public Artist updateArtist(ArtistFormDTO artistDTO, long id) throws IOException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Artist not found");
        }
        Artist artist = repository.getOne(id);
        artist.setName(artistDTO.getName());
        artist.setCountry(artistDTO.getCountry());
        artist.setDescription(artistDTO.getDescription());
        if (!artistDTO.getData().isEmpty()) {
            fileService.deleteFile(artist.getImg());
            artist.setImg(fileService.saveFile(artistDTO.getData()));
        }
        return repository.save(artist);
    }

    @Override
    public void deleteArtist(long id) throws IOException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Artist not found");
        }
        Artist artist = repository.getOne(id);
        if (artist.getReleases().isEmpty()) {
            repository.deleteById(id);
            fileService.deleteFile(artist.getImg());
        }
    }

    // autocomplete
    @Override
    public List<ArtistProjection> getArtistsNames(String query) {
        return repository.findAllBy(query);
    }
}