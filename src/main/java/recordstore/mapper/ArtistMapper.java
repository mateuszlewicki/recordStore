package recordstore.mapper;

import org.springframework.stereotype.Component;
import recordstore.DTO.ArtistDTO;
import recordstore.entity.Artist;

@Component
public class ArtistMapper {

    public ArtistDTO toDTO(Artist artist) {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setId(artist.getId());
        artistDTO.setName(artist.getName());
        artistDTO.setCountry(artist.getCountry());
        artistDTO.setDescription(artist.getDescription());
        artistDTO.setImg(artist.getImg());
        artistDTO.setReleases(artist.getReleases());
        return artistDTO;
    }

    public Artist fromDTO(ArtistDTO artistDTO) {
        Artist artist = new Artist();
        artist.setId(artistDTO.getId());
        artist.setName(artistDTO.getName());
        artist.setCountry(artistDTO.getCountry());
        artist.setDescription(artistDTO.getDescription());
        artist.setImg(artistDTO.getImg());
        artist.setData(artistDTO.getData());
        artist.setReleases(artistDTO.getReleases());
        return artist;
    }
}