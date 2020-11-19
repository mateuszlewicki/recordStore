package recordstore.service;

import org.springframework.stereotype.Service;
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
    public List<LabelProjection> findAllLabels() {
        return repository.findAllBy();
    }
}
