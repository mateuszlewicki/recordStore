package recordstore.service;

import recordstore.entity.Artist;
import recordstore.entity.Label;
import recordstore.entity.Release;

import java.util.List;

public interface SearchService {
    List<String> search(String keyword);
    Release getReleaseByTitle(String title);
    Label getLabelByTitle(String title);
    Artist getArtistByName(String name);
}