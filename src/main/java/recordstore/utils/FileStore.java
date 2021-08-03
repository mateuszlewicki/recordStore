package recordstore.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import recordstore.enums.Bucket;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class FileStore {

    private final AmazonS3 s3;

    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public String save(MultipartFile file) {
        String fileName = createUniqueName(file);
        String path = Bucket.PROFILE_IMAGE.getBucketName();

        Optional<Map<String, String>> optionalMetadata = Optional.of(extractMetadata(file));

        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        try (InputStream inputStream = file.getInputStream()){
            s3.putObject(path, fileName, inputStream, metadata);
            return fileName;
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to store file to s3", e);
        }
    }

    public byte[] download(String key) {
        String path = Bucket.PROFILE_IMAGE.getBucketName();
        try {
            S3Object object = s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download file to s3", e);
        }
    }

    public void deleteFile(final String key) {
        if (!key.equals("noImageAvailable.png")) {
            String path = Bucket.PROFILE_IMAGE.getBucketName();
            s3.deleteObject(path, key);
        }
    }

    private String createUniqueName(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + file.getOriginalFilename();
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }
}