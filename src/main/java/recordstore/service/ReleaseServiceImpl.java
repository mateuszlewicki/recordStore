package recordstore.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Artist;
import recordstore.entity.Genre;
import recordstore.entity.Label;
import recordstore.entity.Release;
import recordstore.repository.ReleaseRepository;
import recordstore.utils.FileUploadUtil;

import java.io.IOException;
import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository repository;

    public ReleaseServiceImpl(ReleaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Release getRelease(long id) {
        return repository.getOne(id);
    }

    @Override
    public void saveRelease(Release release) throws IOException {
        for (Artist artist : release.getArtists()) {
            release.addArtist(artist);
        }
        for (Genre genre : release.getGenres()) {
            release.addGenre(genre);
        }
        release.addLabel(release.getLabel());

        String filename = StringUtils.cleanPath(release.getData().getOriginalFilename());
        String removePicture = release.getImg();

         if(!release.getData().isEmpty()){
             release.setImg(filename);
         }
        repository.save(release);
         if(!release.getData().isEmpty()) {
             FileUploadUtil.saveFile(filename, release.getData());
             FileUploadUtil.deleteFile(removePicture);
         }
    }

    @Override
    public void deleteRelease(long id) throws IOException {
        Release release = repository.getOne(id);
        release.removeLabel(release.getLabel());
        repository.delete(release);
        FileUploadUtil.deleteFile(release.getImg());
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
        return repository.findAll();
    }
}