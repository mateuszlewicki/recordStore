package recordstore.mapstruct.mappers;

import org.mapstruct.Mapper;
import recordstore.DTO.*;
import recordstore.entity.Artist;
import recordstore.entity.Genre;
import recordstore.entity.Label;
import recordstore.entity.Release;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    GenreDTO genreToGenreDTO(Genre genre);
    GenreSlimDTO genreToSLimDTO(Genre genre);

    //ArtistDTO artistToDTO(Artist artist);
    ArtistSlimDTO artistToArtistSLimDTO(Artist artist);

    //LabelDTO labelToLabelDTO(Label label);
    LabelSlimDTO labelTOSlimDTO(Label label);

    //ReleaseDTO releaseToReleaseDTO(Release release);
    ReleaseSlimDTO releaseToReleaseSlimDTO(Release release);
}
