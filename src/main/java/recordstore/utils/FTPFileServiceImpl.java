package recordstore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FTPFileServiceImpl implements FileService {

    @Value("${upload.path}")
    String path;

    @Autowired
    private  DefaultFtpSessionFactory factory;

    @Override
    public void saveFile(String filename, MultipartFile multipartFile) throws IOException {
        try (FtpSession session = factory.getSession();
             InputStream inputStream = multipartFile.getInputStream()) {
            session.write(inputStream, filename);
        } catch (IOException e) {
            throw new IOException("Couldn't save image file" + filename, e);
        }
    }

    @Override
    public void deleteFile(String filename) throws IOException {
        try (FtpSession session = factory.getSession()) {
            if (session.exists(filename)) {
                session.remove(filename);
            }
            Path path1 = Paths.get(path + filename);
            if (Files.exists(path1) && !filename.equals("noImageAvailable.png")) {
                Files.delete(path1);
            }
        } catch (IOException e) {
            throw new IOException("Couldn't remove image file" + filename, e);
        }
    }
}