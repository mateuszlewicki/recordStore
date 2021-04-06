package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Genre;
import recordstore.entity.Release;
import recordstore.repository.ReleaseRepository;
import recordstore.utils.FileService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final String DIRECTORY ="releases/";

    private final ReleaseRepository repository;

    private final FileService fileService;

    public ReleaseServiceImpl(ReleaseRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @Override
    public Release getRelease(long id) {
        return repository.getOne(id);
    }

    @Override
    public boolean isPresent(long id) {
        return repository.existsById(id);
    }

    @Override
    public void saveRelease(Release release) throws IOException {
        String filename = createUniqueName(release.getData());
        String removePicture = release.getImg();

         if(!release.getData().isEmpty()) {
             release.setImg(filename);
         }
        repository.save(release);
         if(!release.getData().isEmpty()) {
             fileService.saveFile(filename, DIRECTORY, release.getData());
             fileService.deleteFile(removePicture, DIRECTORY);
         }
    }

    @Override
    public void deleteRelease(long id) throws IOException {
        Release release = repository.getOne(id);
        if (release.getCollections().size() == 0 && release.getWantlists().size() == 0) {
            release.removeLabel(release.getLabel());
            repository.delete(release);
            fileService.deleteFile(release.getImg(), DIRECTORY);
        }
    }

    @Override
    public Page<Release> getAllReleases(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Release> getReleasesByGenre(long id, Pageable pageable) {
        return repository.findReleasesByGenres_id(id, pageable);
    }

    @Override
    public Page<Release> getReleasesByArtist(long id, Pageable pageable) {
        return repository.findReleasesByArtists_id(id, pageable);
    }

    @Override
    public Page<Release> getReleasesByLabel(long id, Pageable pageable) {
        return repository.findReleasesByLabel_Id(id, pageable);
    }

    @Override
    public Page<Release> getCollectionByAccount(long id, Pageable pageable) {
        return repository.findReleasesByCollections_id(id, pageable);
    }

    @Override
    public Page<Release> getWantListByAccount(long id, Pageable pageable) {
        return repository.findReleasesByWantlists_id(id, pageable);
    }

    @Override
    public List<String> search(String keyword) {
        return repository.search(keyword);
    }

    @Override
    public Release getReleaseByTitle(String title) {
        return repository.findReleaseByTitle(title);
    }

    private String createUniqueName (MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + file.getOriginalFilename();
    }
}