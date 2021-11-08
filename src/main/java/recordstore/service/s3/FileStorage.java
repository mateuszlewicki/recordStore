package recordstore.service.s3;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

    String save(MultipartFile file);
    byte[] download(String key);
    void deleteFile(final String key);
}