package recordstore.entity;

import javax.persistence.*;

@Entity
@Table(name = "genres", schema = "recordstore")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;
}
