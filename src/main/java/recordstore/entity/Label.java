package recordstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(name = "description" , length = 2000)
    private String description;

    @Column(name = "img")
    private String img = "noImageAvailable.png";

    @JsonIgnore
    @OneToMany(mappedBy = "label")
    private List<Release> releases = new ArrayList<>();
}