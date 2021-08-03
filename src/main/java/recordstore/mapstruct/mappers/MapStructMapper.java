package recordstore.mapstruct.mappers;

import org.mapstruct.Mapper;
import recordstore.DTO.*;
import recordstore.entity.*;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    GenreDTO genreToGenreDTO(Genre genre);
    GenreSlimDTO genreToSLimDTO(Genre genre);

    ArtistDTO artistToArtistDTO(Artist artist);
    ArtistSlimDTO artistToArtistSLimDTO(Artist artist);

    LabelDTO labelToLabelDTO(Label label);
    LabelSlimDTO labelTOSlimDTO(Label label);

    AccountDTO accountToAccountDTO(Account account);

    ReleaseDTO releaseToReleaseDTO(Release release);
}