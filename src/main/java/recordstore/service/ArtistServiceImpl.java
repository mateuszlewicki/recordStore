package recordstore.service;

import org.springframework.stereotype.Service;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;
import recordstore.repository.ArtistRepository;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository repository;

    public ArtistServiceImpl(ArtistRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveArtist(Artist artist) {
        repository.save(artist);
    }

    @Override
    public void deleteArtist(long id) {
        repository.deleteById(id);
    }

    @Override
    public Artist getArtist(long id) {
        return repository.getOne(id);
    }

    @Override
    public List<ArtistProjection> getAllArtists() {
        return repository.findAllBy();
    }

//    @Override
//    public List<String> search(String keyword) {
//        return repository.search(keyword);
//    }
}