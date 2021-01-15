package recordstore.mapper;

import org.springframework.stereotype.Component;
import recordstore.DTO.ReleaseDTO;
import recordstore.entity.*;

@Component
public class ReleaseMapper {

    public ReleaseDTO toDTO(Release release) {
        ReleaseDTO releaseDTO = new ReleaseDTO();
        releaseDTO.setId(release.getId());
        releaseDTO.setCode(release.getCode());
        releaseDTO.setTitle(release.getTitle());
        releaseDTO.setReleaseDate(release.getReleaseDate());
        releaseDTO.setFormat(release.getFormat());
        releaseDTO.setImg(release.getImg());
        releaseDTO.setArtists(release.getArtists());
        releaseDTO.setGenres(release.getGenres());
        releaseDTO.setLabel(release.getLabel());
        releaseDTO.setTracklist(release.getTracklist());
        releaseDTO.setPlaylist(release.getPlaylist());
        return releaseDTO;
    }

    public Release fromDTO(ReleaseDTO releaseDTO) {
        Release release = new Release();
        release.setId(releaseDTO.getId());
        release.setCode(releaseDTO.getCode());
        release.setTitle(releaseDTO.getTitle());
        release.setReleaseDate(releaseDTO.getReleaseDate());
        release.setFormat(releaseDTO.getFormat());
        release.setImg(releaseDTO.getImg());
        release.setData(releaseDTO.getData());

        for (Artist artist : releaseDTO.getArtists()) {
            release.addArtist(artist);
        }
        for (Genre genre : releaseDTO.getGenres()) {
            release.addGenre(genre);
        }

        release.addLabel(releaseDTO.getLabel());

        for (Track track : releaseDTO.getTracklist()) {
            release.addTrack(track);
        }

        for (YouTubeVideo video : releaseDTO.getPlaylist()) {
            release.addVideo(video);
        }

        return release;
    }
}
