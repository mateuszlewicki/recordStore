package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ArtistDTO;
import recordstore.DTO.ArtistFormDTO;
import recordstore.entity.Artist;

public interface ArtistService {

    Artist getArtist(long id);
    Page<ArtistDTO> getAllArtists(Pageable pageable);
    Page<ArtistDTO> search(String keyword, Pageable pageable);

    Artist createArtist(ArtistFormDTO artistDTO);
    Artist updateArtist(ArtistFormDTO artistDTO, long id);
    void deleteArtist(long id);

    Artist uploadImage(long id, MultipartFile file);
    byte[] downloadImage(long id);
}