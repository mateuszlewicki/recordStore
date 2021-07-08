package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import recordstore.DTO.GenreDTO;
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
    public void createGenre(GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setTitle(genreDTO.getTitle());
        repository.save(genre);
    }

    @Override
    public void updateGenre(GenreDTO genreDTO, long id) {
        Genre genre = repository.getOne(id);
        genre.setTitle(genreDTO.getTitle());
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
    public Page<Genre> getAllGenres(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<GenreProjection> getGenresTitles(String query) {
        return repository.findAllBy(query);
    }
}