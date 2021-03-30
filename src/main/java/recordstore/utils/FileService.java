package recordstore.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileService {

    @Value("${upload.path}")
    String path;

    public void saveFile(String filename, String directory, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(path + directory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Couldn't save image file" + filename, ioe);
        }
    }

    public void deleteFile(String filename, String directory) throws IOException {
        if (!filename.equals("noImageAvailable.png")) {
            Path path1 = Paths.get(path + directory + filename);
            if (Files.exists(path1)) {
                Files.delete(path1);
            }
        }
    }
}
