package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.DTO.GenreFormDTO;
import recordstore.entity.Genre;

public interface GenreService {

    Page<Genre> getAllGenres(Pageable pageable);
    Genre getGenre(long id);

    Genre createGenre(GenreFormDTO genreDTO);
    Genre updateGenre(GenreFormDTO genreDTO, long id);
    void deleteGenre(long id);
}