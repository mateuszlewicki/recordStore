package recordstore.service;

import recordstore.entity.Genre;
import recordstore.projections.GenreProjection;

import java.util.List;

public interface GenreService {

    void saveGenre(Genre genre);
    void deleteGenre(long id);

    Genre getGenre(long id);
    List<Genre> getAllGenres();
    List<GenreProjection> getGenresTitles(String query);
}
