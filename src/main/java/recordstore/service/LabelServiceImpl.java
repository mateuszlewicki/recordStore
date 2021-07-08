package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import recordstore.DTO.LabelDTO;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;
import recordstore.repository.LabelRepository;
import recordstore.utils.FileService;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository repository;

    private final FileService fileService;

    public LabelServiceImpl(LabelRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @Override
    public void createLabel(LabelDTO labelDTO) throws IOException {
        Label label = new Label();
        label.setTitle(labelDTO.getTitle());
        label.setCountry(labelDTO.getCountry());
        label.setDescription(labelDTO.getDescription());
        if(!labelDTO.getData().isEmpty()) {
            label.setImg(fileService.saveFile(labelDTO.getData()));
        }
        repository.save(label);
    }

    @Override
    public void updateLabel(LabelDTO labelDTO, long id) throws IOException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        Label label = repository.getOne(id);
        label.setTitle(labelDTO.getTitle());
        label.setCountry(labelDTO.getCountry());
        label.setDescription(labelDTO.getDescription());
        if(!labelDTO.getData().isEmpty()) {
            fileService.deleteFile(label.getImg());
            label.setImg(fileService.saveFile(labelDTO.getData()));
        }
        repository.save(label);
    }

    @Override
    public void deleteLabel(long id) throws IOException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        Label label = repository.getOne(id);
        if (label.getReleases().isEmpty()) {
            repository.deleteById(id);
            fileService.deleteFile(label.getImg());
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
}