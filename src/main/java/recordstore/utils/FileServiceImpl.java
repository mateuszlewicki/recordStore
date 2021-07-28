package recordstore.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileServiceImpl implements FileService {

    @Value("${upload.path}")
    String path;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String filename = createUniqueName(multipartFile);
        Path uploadPath = Paths.get(path);
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException ioe) {
            throw new IOException("Couldn't save image file" + filename, ioe);
        }
    }

    public void deleteFile(String filename) throws IOException {
        if (!filename.equals("noImageAvailable.png")) {
            Path path1 = Paths.get(path + filename);
            if (Files.exists(path1)) {
                Files.delete(path1);
            }
        }
    }

    private String createUniqueName(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + file.getOriginalFilename();
    }
}