package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import recordstore.DTO.GenreFormDTO;
import recordstore.entity.Genre;
import recordstore.repository.GenreRepository;
import recordstore.repository.ReleaseRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository repository;

    private ReleaseRepository releaseRepository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setReleaseRepository(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
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
    public Genre updateGenre( long id, GenreFormDTO genreDTO) {
        Genre genre = repository.findGenreById(id);
        if (genre == null) {
            throw new EntityNotFoundException("Genre not found");
        }
        genre.setTitle(genreDTO.getTitle());
        return repository.save(genre);
    }

    @Override
    public void deleteGenre(long id) {
        Genre genre = repository.findGenreById(id);
        if (genre == null) {
            throw new EntityNotFoundException("Genre not found");
        }
        if (!releaseRepository.existsReleasesByGenres_id(id)) {
            repository.deleteById(id);
        }
    }
}