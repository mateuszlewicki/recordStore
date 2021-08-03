package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import recordstore.DTO.GenreFormDTO;
import recordstore.entity.Genre;
import recordstore.repository.GenreRepository;
import javax.persistence.EntityNotFoundException;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository repository;
    private final ReleaseService releaseService;

    public GenreServiceImpl(GenreRepository repository, ReleaseService releaseService) {
        this.repository = repository;
        this.releaseService = releaseService;
    }

    @Override
    public Page<Genre> getAllGenres(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Genre getGenre(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Genre not found");
        }
        return repository.getOne(id);
    }

    @Override
    public Genre createGenre(GenreFormDTO genreDTO) {
        Genre genre = new Genre();
        genre.setTitle(genreDTO.getTitle());
        return repository.save(genre);
    }

    @Override
    public Genre updateGenre( long id, GenreFormDTO genreDTO) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Genre not found");
        }
        Genre genre = repository.getOne(id);
        genre.setTitle(genreDTO.getTitle());
        return repository.save(genre);
    }

    @Override
    public void deleteGenre(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Genre not found");
        }
        Genre genre = repository.getOne(id);
        if (releaseService.countReleasesByGenre(genre) == 0) {
            repository.deleteById(id);
        }
    }
}