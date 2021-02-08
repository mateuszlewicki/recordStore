package recordstore.service;

import recordstore.entity.Genre;

import java.util.List;

public interface GenreService {

    void saveGenre(Genre genre);
    void deleteGenre(long id);

    Genre getGenre(long id);
    boolean isPresent(long id);
    List<Genre> getAllGenres();
}
