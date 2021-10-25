package recordstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.DTO.LabelFormDTO;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;
import recordstore.repository.LabelRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository repository;
    private final FileStore fileStore;

    public LabelServiceImpl(LabelRepository repository, FileStore fileStore) {
        this.repository = repository;
        this.fileStore = fileStore;
    }

    @Override
    public Label getLabel(long id) {
        Label label = repository.findLabelById(id);
        if (label == null) {
            throw new EntityNotFoundException("Label not found");
        }
        return label;
    }

    @Override
    public Page<LabelProjection> getAllLabels(Pageable pageable) {
        return repository.findAllBy(pageable);
    }

    @Override
    public Page<LabelProjection> search(String keyword, Pageable pageable) {
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
        Label label = getLabel(id);
        label.setTitle(labelDTO.getTitle());
        label.setCountry(labelDTO.getCountry());
        label.setDescription(labelDTO.getDescription());
        return repository.save(label);
    }

    @Override
    public void deleteLabel(long id) {
        Label label = getLabel(id);
        if (label.getReleases().isEmpty()) {
            repository.deleteById(id);
            fileStore.deleteFile(label.getImg());
        }
    }

    @Override
    public Label uploadImage(long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload file:(");
        }
        Label label = getLabel(id);
        String fileName = fileStore.save(file);
        fileStore.deleteFile(label.getImg());
        label.setImg(fileName);
        return repository.save(label);
    }

    @Override
    public byte[] downloadImage(long id) {
        Label label = getLabel(id);
        return fileStore.download(label.getImg());
    }
}