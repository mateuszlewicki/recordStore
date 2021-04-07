package recordstore.service;

import org.springframework.stereotype.Service;
import recordstore.entity.Artist;
import recordstore.entity.Label;
import recordstore.entity.Release;
import recordstore.repository.ArtistRepository;
import recordstore.repository.LabelRepository;
import recordstore.repository.ReleaseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final ReleaseRepository releaseRepository;
    private final LabelRepository labelRepository;
    private final ArtistRepository artistRepository;

    public SearchServiceImpl(ReleaseRepository releaseRepository,
                             LabelRepository labelRepository,
                             ArtistRepository artistRepository) {
        this.releaseRepository = releaseRepository;
        this.labelRepository = labelRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<String> search(String keyword) {
        List<String> list = new ArrayList<>();
        list.addAll(releaseRepository.search(keyword));
        list.addAll(labelRepository.search(keyword));
        list.addAll(artistRepository.search(keyword));
        return list;
    }

    @Override
    public Release getReleaseByTitle(String title) {
        return releaseRepository.findReleaseByTitle(title);
    }

    @Override
    public Label getLabelByTitle(String title) {
        return labelRepository.findLabelByTitle(title);
    }

    @Override
    public Artist getArtistByName(String name) {
        return artistRepository.findArtistByName(name);
    }
}