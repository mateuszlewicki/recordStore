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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "position")
    String position;

    @Column(name = "title")
    String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Release release;
}
