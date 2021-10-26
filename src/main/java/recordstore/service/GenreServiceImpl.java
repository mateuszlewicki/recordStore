package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import recordstore.DTO.GenreFormDTO;
import recordstore.entity.Genre;
import recordstore.repository.GenreRepository;
import javax.persistence.EntityNotFoundException;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Genre> getAllGenres(Pageable pageable) {
        return repository.findAllGenres(pageable);
    }

    @Override
    public Genre getGenre(long id) {
        Genre genre = repository.findGenreById(id);
        if (genre == null) {
            throw new EntityNotFoundException("Genre not found");
        }
        return genre;
    }

    @Override
    public Genre createGenre(GenreFormDTO genreDTO) {
        Genre genre = new Genre();
        genre.setTitle(genreDTO.getTitle());
        return repository.save(genre);
    }

    @Override
    public Genre updateGenre(GenreFormDTO genreDTO, long id) {
        Genre genre = getGenre(id);
        genre.setTitle(genreDTO.getTitle());
        return repository.save(genre);
    }

    @Override
    public void deleteGenre(long id) {
        Genre genre = getGenre(id);
        if (genre.getReleases().isEmpty()) {
            repository.deleteById(id);
        }
    }
}