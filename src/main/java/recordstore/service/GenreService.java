package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import recordstore.DTO.GenreDTO;
import recordstore.entity.Genre;
import recordstore.projections.GenreProjection;

import java.util.List;

public interface GenreService {

    void createGenre(GenreDTO genreDTO);
    void updateGenre(GenreDTO genreDTO, long id);
    void deleteGenre(long id);

    Genre getGenre(long id);
    Page<Genre> getAllGenres(Pageable pageable);
    List<GenreProjection> getGenresTitles(String query);
}