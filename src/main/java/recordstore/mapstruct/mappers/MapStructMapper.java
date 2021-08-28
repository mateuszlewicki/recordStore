package recordstore.mapstruct.mappers;

import org.mapstruct.Mapper;
import recordstore.DTO.*;
import recordstore.entity.*;
import recordstore.projections.ArtistProjection;
import recordstore.projections.LabelProjection;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    GenreDTO genreToGenreDTO(Genre genre);
    GenreSlimDTO genreToSLimDTO(Genre genre);

    ArtistDTO artistToArtistDTO(Artist artist);
    ArtistSlimDTO artistToArtistSLimDTO(Artist artist);
    ArtistSlimDTO artistProjectionToArtistSlimDTO(ArtistProjection artistProjection);

    LabelDTO labelToLabelDTO(Label label);
    LabelSlimDTO labelTOSlimDTO(Label label);
    LabelSlimDTO labelProjectionToLabelSlimDTO(LabelProjection labelProjection);

    AccountDTO accountToAccountDTO(Account account);

    ReleaseDTO releaseToReleaseDTO(Release release);
}