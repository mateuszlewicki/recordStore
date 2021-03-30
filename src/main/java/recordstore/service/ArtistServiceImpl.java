package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;
import recordstore.repository.ArtistRepository;
import recordstore.utils.FileService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final String DIRECTORY ="artists/";

    private final ArtistRepository repository;

    private final FileService fileService;

    public ArtistServiceImpl(ArtistRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @Override
    public void saveArtist(Artist artist) throws IOException {
        String filename = createUniqueName(artist.getData());
        String removeImg = artist.getImg();
        if (!artist.getData().isEmpty()) {
            artist.setImg(filename);
        }
        repository.save(artist);
        if(!artist.getData().isEmpty()) {
            fileService.saveFile(filename, DIRECTORY, artist.getData());
            fileService.deleteFile(removeImg, DIRECTORY);
        }
    }

    @Override
    public void deleteArtist(long id) throws IOException {
        Artist artist = repository.getOne(id);
        if (artist.getReleases().size() == 0) {
            repository.deleteById(id);
            fileService.deleteFile(artist.getImg(), DIRECTORY);
        }
    }

    @Override
    public Artist getArtist(long id) {
        return repository.getOne(id);
    }

    @Override
    public boolean isPresent(long id) {
        return repository.existsById(id);
    }

    @Override
    public List<ArtistProjection> getAllArtistsNames() {
        return repository.findAllBy();
    }

    @Override
    public Page<Artist> getAllArtists(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<String> search(String keyword) {
        return repository.search(keyword);
    }

    @Override
    public Artist getArtistByName(String name) {
        return repository.findArtistByName(name);
    }

    private String createUniqueName (MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + file.getOriginalFilename();
    }
}