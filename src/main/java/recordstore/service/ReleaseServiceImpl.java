package recordstore.service;

import org.springframework.stereotype.Service;
import recordstore.entity.Artist;
import recordstore.entity.Genre;
import recordstore.entity.Label;
import recordstore.entity.Release;
import recordstore.repository.ArtistRepository;
import recordstore.repository.GenreRepository;
import recordstore.repository.LabelRepository;
import recordstore.repository.ReleaseRepository;

import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;
    private final LabelRepository labelRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;

    public ReleaseServiceImpl(ReleaseRepository releaseRepository,
                              LabelRepository labelRepository,
                              ArtistRepository artistRepository,
                              GenreRepository genreRepository) {
        this.releaseRepository = releaseRepository;
        this.labelRepository = labelRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
    }


    @Override
    public Release getRelease(long id) {
        return releaseRepository.getOne(id);
    }

    @Override
    public void saveRelease(Release release) {
        for (Artist artist : release.getArtists()) {
            release.addArtist(artist);
        }
        for (Genre genre : release.getGenres()) {
            release.addGenre(genre);
        }
        release.addLabel(release.getLabel());
        releaseRepository.save(release);
    }

    @Override
    public void deleteRelease(long id) {
        releaseRepository.deleteById(id);
    }

//    old methods
//    @Override
//    public List<Release> getAllReleasesByGenre(String genre) {
//        return repository.findAllByGenre(genre);
//    }

//    @Override
//    public List<Release> getAllReleasesByArtist(String artist) {
//        return repository.findAllByArtist(artist);
//    }

    @Override
    public List<Release> getAllReleases() {
        return releaseRepository.findAll();
    }
}