package recordstore.projections;

import recordstore.enums.Format;

import java.util.Set;

public interface ReleaseProjection {

    long getId();
    String getCode();
    String getTitle();
    String getReleaseDate();
    Format getFormat();
    String getImg();
    LabelView getLabel();
    Set<ArtistView> getArtists();
    Set<GenreView> getGenres();

    interface ArtistView{
        String getName();
    }

    interface GenreView{
        String getTitle();
    }

    interface LabelView{
        String getTitle();
    }
}