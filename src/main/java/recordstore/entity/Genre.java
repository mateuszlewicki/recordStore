package recordstore.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "releases")
@Table(name = "genres", schema = "recordstore")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "title")
    @NotBlank(message = "Field is mandatory")
    private String title;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Release> releases = new HashSet<>();
}