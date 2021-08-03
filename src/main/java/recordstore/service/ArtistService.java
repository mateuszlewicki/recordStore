package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ArtistFormDTO;
import recordstore.entity.Artist;
import java.io.IOException;

public interface ArtistService {

    Artist getArtist(long id);
    Page<Artist> getAllArtists(Pageable pageable);
    Page<Artist> search(String keyword, Pageable pageable);

    Artist createArtist(ArtistFormDTO artistDTO) throws IOException;
    Artist updateArtist(ArtistFormDTO artistDTO, long id) throws IOException;
    void deleteArtist(long id) throws IOException;

    Artist uploadImage(long id, MultipartFile file);
    byte[] downloadImage(long id);
}