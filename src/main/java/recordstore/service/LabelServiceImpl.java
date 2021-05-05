package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;
import recordstore.repository.LabelRepository;
import recordstore.utils.FileService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class LabelServiceImpl implements LabelService {

    private final String DIRECTORY ="labels/";

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
            fileService.saveFile(filename, DIRECTORY, label.getData());
            fileService.deleteFile(removePicture, DIRECTORY);
        }
    }

    @Override
    public void deleteLabel(long id) throws IOException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        Label label = repository.getOne(id);
        if (label.getReleases().isEmpty()) {
            repository.deleteById(id);
            fileService.deleteFile(label.getImg(), DIRECTORY);
        }
    }

    @Override
    public Label getLabel(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        return repository.getOne(id);
    }

    @Override
    public List<LabelProjection> getLabelsTitles(String query) {
        return repository.findAllBy(query);
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