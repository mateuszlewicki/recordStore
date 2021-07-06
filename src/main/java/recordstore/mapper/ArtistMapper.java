package recordstore.mapper;

import org.springframework.data.domain.Page;
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

    public Page<ArtistDTO> toDTOs(Page<Artist> artists) {
        return  artists.map(this::toDTO);
    }
}