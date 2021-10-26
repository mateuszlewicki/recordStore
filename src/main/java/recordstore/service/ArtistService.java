package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ArtistFormDTO;
import recordstore.entity.Artist;
import recordstore.projections.ArtistProjection;

import java.io.IOException;

public interface ArtistService {

    Artist getArtist(long id);
    Page<ArtistProjection> getAllArtists(Pageable pageable);
    Page<ArtistProjection> search(String keyword, Pageable pageable);

    Artist createArtist(ArtistFormDTO artistDTO);
    Artist updateArtist(ArtistFormDTO artistDTO, long id);
    void deleteArtist(long id);

    Artist uploadImage(long id, MultipartFile file);
    byte[] downloadImage(long id);
}