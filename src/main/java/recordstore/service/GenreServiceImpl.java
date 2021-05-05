package recordstore.service;

import org.springframework.stereotype.Service;
import recordstore.entity.Genre;
import recordstore.projections.GenreProjection;
import recordstore.repository.GenreRepository;

import javax.persistence.EntityNotFoundException;
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
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Genre not found");
        }
        Genre genre = repository.getOne(id);
        if (genre.getReleases().isEmpty()) {
            repository.deleteById(id);
        }
    }

    @Override
    public Genre getGenre(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Genre not found");
        }
        return repository.getOne(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        return repository.findAll();
    }

    @Override
    public List<GenreProjection> getGenresTitles(String query) {
        return repository.findAllBy(query);
    }
}
