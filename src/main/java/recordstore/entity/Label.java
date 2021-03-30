package recordstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "labels", schema = "recordstore")
public class Label {

    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    @NotBlank(message = "Field is mandatory")
    private String title;

    @Column(name = "country")
    private String country;

    @Column(name = "description")
    private String description;

    @Column(name = "img")
    private String img;

    @Transient
    private MultipartFile data;

    @OneToMany(mappedBy = "label", orphanRemoval = false)
    private List<Release> releases = new ArrayList<>();
}