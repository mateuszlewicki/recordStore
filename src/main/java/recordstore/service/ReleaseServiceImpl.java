package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.ReleaseDTO;
import recordstore.entity.Artist;
import recordstore.entity.Genre;
import recordstore.entity.Release;
import recordstore.entity.Track;
import recordstore.repository.ReleaseRepository;
import recordstore.utils.FileService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ReleaseServiceImpl implements ReleaseService {

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
    public void saveRelease(Release release) throws IOException {
        String filename = createUniqueName(release.getData());
        String removePicture = release.getImg();

         if(!release.getData().isEmpty()){
             release.setImg(filename);
         } else {
             release.setImg(release.getImg());
         }
        repository.save(release);
         if(!release.getData().isEmpty()) {
             fileService.saveFile(filename, release.getData());
             fileService.deleteFile(removePicture);
         }
    }

    @Override
    public void deleteRelease(long id) throws IOException {
        Release release = repository.getOne(id);
        release.removeLabel(release.getLabel());
        repository.delete(release);
        fileService.deleteFile(release.getImg());
    }

    @Override
    public Page<Release> getAllReleases(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Release> getAllReleasesByGenre(Genre genre, Pageable pageable) {
        return repository.findAllByGenres(genre, pageable);
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