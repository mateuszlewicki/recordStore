package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;
import recordstore.repository.LabelRepository;
import recordstore.utils.FileService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository repository;

    private final FileService fileService;

    public LabelServiceImpl(LabelRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @Override
    public void saveLabel(Label label) throws IOException {
        String filename = createUniqueName(label.getData());
        String removePicture = label.getImg();

        if(!label.getData().isEmpty()) {
            label.setImg(filename);
        }
        repository.save(label);
        if(!label.getData().isEmpty()) {
            fileService.saveFile(filename, label.getData());
            fileService.deleteFile(removePicture);
        }
    }

    @Override
    public void deleteLabel(long id) throws IOException {
        Label label = repository.getOne(id);
        if (label.getReleases().size() == 0) {
            repository.deleteById(id);
            fileService.deleteFile(label.getImg());
        }
    }

    @Override
    public Label getLabel(long id) {
        return repository.getOne(id);
    }

    @Override
    public boolean isPresent(long id) {
        return repository.existsById(id);
    }

    @Override
    public List<LabelProjection> getAllLabelsTitles() {
        return repository.findAllBy();
    }

    @Override
    public Page<Label> getAllLabels(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<String> search(String keyword) {
        return repository.search(keyword);
    }

    @Override
    public Label getLabelByTitle(String title) {
        return repository.findLabelByTitle(title);
    }

    private String createUniqueName (MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + file.getOriginalFilename();
    }
}