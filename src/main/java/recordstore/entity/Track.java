package recordstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tracks", schema = "recordstore")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "position")
    @NotBlank(message = "Field is mandatory")
    String position;

    @Column(name = "title")
    @NotBlank(message = "Field is mandatory")
    String title;
}