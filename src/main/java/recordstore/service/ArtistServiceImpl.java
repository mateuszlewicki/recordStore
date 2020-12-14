package recordstore.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;
import recordstore.repository.ArtistRepository;
import recordstore.utils.FileUploadUtil;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ArtistServiceImpl implements ArtistService {

    @Value("${upload.path}")
    String path;

    private final ArtistRepository repository;

    public ArtistServiceImpl(ArtistRepository repository) {
        this.repository = repository;
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
            FileUploadUtil.saveFile(filename, artist.getData(), path);
            FileUploadUtil.deleteFile(removeImg, path);
        }
    }

    @Override
    public void deleteArtist(long id) throws IOException {
        Artist artist = repository.getOne(id);
        if (artist.getReleases().size() == 0) {
            repository.deleteById(id);
            FileUploadUtil.deleteFile(artist.getImg(), path);
        }
    }

    @Override
    public Artist getArtist(long id) {
        return repository.getOne(id);
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