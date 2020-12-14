package recordstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recordstore.entity.Label;
import recordstore.projections.LabelProjection;
import recordstore.repository.LabelRepository;
import recordstore.utils.FileUploadUtil;
import recordstore.utils.FtpUploader;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class LabelServiceImpl implements LabelService {

    @Value("${upload.path}")
    String path;

//    @Autowired
//    DefaultFtpSessionFactory factory;

    private final LabelRepository repository;

    public LabelServiceImpl(LabelRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveLabel(Label label) throws IOException {
        String filename = createUniqueName(label.getData());
        String removePicture = label.getImg();

        if(!label.getData().isEmpty()) {
            label.setImg(filename);
        }
        repository.save(label);
        if(!label.getData().isEmpty()) {
            FileUploadUtil.saveFile(filename, label.getData(), path);
            FileUploadUtil.deleteFile(removePicture, path);
            //FtpUploader.uploadToFTP(filename, label.getData(), factory);
        }
    }

    @Override
    public void deleteLabel(long id) throws IOException {
        Label label = repository.getOne(id);
        if (label.getReleases().size() == 0) {
            repository.deleteById(id);
            FileUploadUtil.deleteFile(label.getImg(), path);
        }
    }

    @Override
    public Label getLabel(long id) {
        return repository.getOne(id);
    }

    @Override
    public List<LabelProjection> getAllLabelsTitles() {
        return repository.findAllBy();
    }

    @Override
    public Page<Label> getAllLabels(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<String> search(String keyword) {
        return repository.search(keyword);
    }

    @Override
    public Label getLabelByTitle(String title) {
        return repository.findLabelByTitle(title);
    }

    private String createUniqueName (MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String resultFilename = uuid + "." + file.getOriginalFilename();
        return resultFilename;
    }
}