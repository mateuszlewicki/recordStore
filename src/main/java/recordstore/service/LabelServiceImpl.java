package recordstore.service;

import org.springframework.stereotype.Service;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;
import recordstore.repository.LabelRepository;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository repository;

    public LabelServiceImpl(LabelRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveLabel(Label label) {
        repository.save(label);
    }

    @Override
    public void deleteLabel(long id) {
        repository.deleteById(id);
    }

    @Override
    public Label getLabel(long id) {
        return repository.getOne(id);
    }

    @Override
    public List<LabelProjection> findAllLabels() {
        return repository.findAllBy();
    }
}