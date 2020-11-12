package recordstore.service;

import recordstore.entity.Record;
import java.util.List;

public interface RecordService {

    Record getRecord(long id);
    void saveRecord(Record record);
    void updateRecord(Record record);
    void deleteRecord(long id);

    List<String> getAllGenres();
    List<Record> getAllRecordsByGenre(String genre);
    List<Record> getAllRecords();
    List<Record> getAllRecordsByArtist(String artist);
}
