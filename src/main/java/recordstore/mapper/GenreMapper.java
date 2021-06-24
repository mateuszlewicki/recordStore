package recordstore.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import recordstore.DTO.GenreDTO;
import recordstore.entity.Genre;

@Component
public class GenreMapper {

    public GenreDTO toDTO(Genre genre) {
       GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setTitle(genre.getTitle());
        genreDTO.setReleases(genre.getReleases());
        return genreDTO;
    }

    public Genre fromDTO(GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setTitle(genreDTO.getTitle());
        genre.setReleases(genreDTO.getReleases());
        return genre;
    }

    public Page<GenreDTO> toDTOs(Page<Genre> genres) {
        return genres.map(this::toDTO);
    }
}