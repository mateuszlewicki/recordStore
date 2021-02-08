package recordstore.service;

import org.springframework.stereotype.Service;
import recordstore.entity.Genre;
import recordstore.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveGenre(Genre genre) {
        repository.save(genre);
    }

    @Override
    public void deleteGenre(long id) {
        Genre genre = repository.getOne(id);
        if (genre.getReleases().size() == 0) {
            repository.deleteById(id);
        }
    }

    @Override
    public Genre getGenre(long id) {
        return repository.getOne(id);
    }

    @Override
    public boolean isPresent(long id) {
        return repository.existsById(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        return repository.findAll();
    }
}
