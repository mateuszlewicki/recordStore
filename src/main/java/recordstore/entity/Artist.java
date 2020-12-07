package recordstore.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "releases")
@Table(name = "artists", schema = "recordstore")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Field is mandatory")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "description")
    private String description;

    @Column(name = "img")
    private String img = "noImageAvailable.png";

    @Transient
    private MultipartFile data;

    @ManyToMany(mappedBy = "artists", fetch = FetchType.LAZY)
    private Set<Release> releases = new HashSet<>();
}