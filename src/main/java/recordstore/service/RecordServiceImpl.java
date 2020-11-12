package recordstore.service;

import org.springframework.stereotype.Service;
import recordstore.entity.Record;
import recordstore.repository.RecordRepository;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository repository;

    public RecordServiceImpl(RecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public Record getRecord(long id) {
        return repository.getOne(id);
    }

    @Override
    public void saveRecord(Record record) {
        repository.save(record);
    }

    @Override
    public void updateRecord(Record record){
        Record updated = repository.getOne(record.getId());
        updated.setId(record.getId());
        updated.setArtist(record.getArtist());
        updated.setTitle(record.getTitle());
        updated.setCode(record.getCode());
        updated.setLabel(record.getLabel());
        updated.setReleaseDate(record.getReleaseDate());
        updated.setGenre(record.getGenre());
        updated.setFormat(record.getFormat());
        updated.setPrice(record.getPrice());
        updated.setImg(record.getImg());
        updated.setQuantity(record.getQuantity());
        repository.save(updated);
    }

    @Override
    public void deleteRecord(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getAllGenres() {
        return repository.findAllGenres();
    }

    @Override
    public List<Record> getAllRecordsByGenre(String genre) {
        return repository.findAllByGenre(genre);
    }

    @Override
    public List<Record> getAllRecordsByArtist(String artist) {
        return repository.findAllByArtist(artist);
    }

    @Override
    public List<Record> getAllRecords() {
        return repository.findAll();
    }

}
