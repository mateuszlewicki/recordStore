package recordstore.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
    private String img = "noImageAvailable.png";
}