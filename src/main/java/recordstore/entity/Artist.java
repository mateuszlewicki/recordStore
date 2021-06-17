package recordstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "releases")
@Table(name = "artists", schema = "recordstore")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Field is mandatory")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "img")
    private String img;

    @Transient
    private MultipartFile data;

    @JsonIgnoreProperties(value = {"artists", "hibernateLazyInitializer"})
    @ManyToMany(mappedBy = "artists", fetch = FetchType.LAZY)
    private Set<Release> releases = new HashSet<>();
}