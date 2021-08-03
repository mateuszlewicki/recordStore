package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.LabelFormDTO;
import recordstore.entity.Label;
import recordstore.repository.LabelRepository;
import recordstore.utils.FileStore;
import javax.persistence.EntityNotFoundException;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository repository;
    private final FileStore fileStore;
    private final ReleaseService releaseService;

    public LabelServiceImpl(LabelRepository repository, FileStore fileStore, ReleaseService releaseService) {
        this.repository = repository;
        this.fileStore = fileStore;
        this.releaseService = releaseService;
    }

    @Override
    public Label getLabel(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        return repository.getOne(id);
    }

    @Override
    public Page<Label> getAllLabels(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Label> search(String keyword, Pageable pageable) {
        return repository.search(keyword, pageable);
    }

    @Override
    public Label createLabel(LabelFormDTO labelDTO) {
        Label label = new Label();
        label.setTitle(labelDTO.getTitle());
        label.setCountry(labelDTO.getCountry());
        label.setDescription(labelDTO.getDescription());
        return repository.save(label);
    }

    @Override
    public Label updateLabel(LabelFormDTO labelDTO, long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        Label label = repository.getOne(id);
        label.setTitle(labelDTO.getTitle());
        label.setCountry(labelDTO.getCountry());
        label.setDescription(labelDTO.getDescription());
        return repository.save(label);
    }

    @Override
    public void deleteLabel(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        Label label = repository.getOne(id);
        if (releaseService.countReleasesByLabel(label) == 0) {
            repository.deleteById(id);
            fileStore.deleteFile(label.getImg());
        }
    }

    @Override
    public Label uploadImage(long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        Label label = repository.getOne(id);
        String fileName = fileStore.save(file);
        fileStore.deleteFile(label.getImg());
        label.setImg(fileName);
        return repository.save(label);
    }

    @Override
    public byte[] downloadImage(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Label not found");
        }
        Label label = repository.getOne(id);
        return fileStore.download(label.getImg());
    }
}