package recordstore.projections;

import recordstore.enums.Format;

public interface ReleaseProjection {

    long getId();

    String getCode();

    String getTitle();

    String getReleaseDate();

    Format getFormat();

    String getImg();
}