package recordstore.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import recordstore.DTO.LabelDTO;
import recordstore.DTO.ReleaseDTO;
import recordstore.DTO.TrackDTO;
import recordstore.DTO.YouTubeVideoDTO;
import recordstore.entity.*;

import java.util.stream.Collectors;

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
        releaseDTO.setLabel(get(release.getLabel()));
        //releaseDTO.setLabel(release.getLabel());
        releaseDTO.setTracklist(release.getTracklist().stream().map(this::trackToDTO).collect(Collectors.toList()));
        releaseDTO.setPlaylist(release.getPlaylist().stream().map(this::videoToDTO).collect(Collectors.toList()));
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

        for (TrackDTO trackDTO : releaseDTO.getTracklist()) {
            release.addTrack(trackFromDTO(trackDTO));
        }

        for (YouTubeVideoDTO videoDTO : releaseDTO.getPlaylist()) {
            release.addVideo(videoFromDTO(videoDTO));
        }

        return release;
    }

    public Page<ReleaseDTO> toDTOs(Page<Release> releases) {
        return releases.map(this::toDTO);
    }

    private TrackDTO trackToDTO(Track track) {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(track.getId());
        trackDTO.setPosition(track.getPosition());
        trackDTO.setTitle(track.getTitle());
        return trackDTO;
    }

    private Track trackFromDTO(TrackDTO trackDTO) {
        Track track = new Track();
        track.setId(trackDTO.getId());
        track.setPosition(trackDTO.getPosition());
        track.setTitle(trackDTO.getTitle());
        return track;
    }

    private YouTubeVideoDTO videoToDTO(YouTubeVideo video) {
        YouTubeVideoDTO videoDTO = new YouTubeVideoDTO();
        videoDTO.setId(videoDTO.getId());
        videoDTO.setVideoId(video.getVideoId());
        return videoDTO;
    }

    private YouTubeVideo videoFromDTO(YouTubeVideoDTO videoDTO) {
        YouTubeVideo video = new YouTubeVideo();
        video.setId(videoDTO.getId());
        video.setVideoId(videoDTO.getVideoId());
        return video;
    }

    private Label get(Label label) {
        Label l = new Label();
        l.setId(label.getId());
        l.setTitle(label.getTitle());
        l.setCountry(label.getCountry());
        l.setDescription(label.getDescription());
        return l;
    }
}