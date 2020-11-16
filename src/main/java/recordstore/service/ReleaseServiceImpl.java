package recordstore.service;

import org.springframework.stereotype.Service;
import recordstore.entity.Release;
import recordstore.repository.ReleaseRepository;

import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository repository;

    public ReleaseServiceImpl(ReleaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Release getRelease(long id) {
        return repository.getOne(id);
    }

    @Override
    public void saveRelease(Release release) {
        repository.save(release);
    }

    @Override
    public void deleteRelease(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getAllGenres() {
        return repository.findAllGenres();
    }

    @Override
    public List<Release> getAllReleasesByGenre(String genre) {
        return repository.findAllByGenre(genre);
    }

    @Override
    public List<Release> getAllReleasesByArtist(String artist) {
        return repository.findAllByArtist(artist);
    }

    @Override
    public List<Release> getAllReleases() {
        return repository.findAll();
    }
}
