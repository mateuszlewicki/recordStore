package recordstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "releases")
@Table(name = "genres", schema = "recordstore")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    @NotBlank(message = "Field is mandatory")
    private String title;

    @JsonIgnoreProperties(value = {"genres", "hibernateLazyInitializer"})
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Release> releases = new HashSet<>();
}