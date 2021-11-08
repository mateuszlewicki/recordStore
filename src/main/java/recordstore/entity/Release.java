package recordstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import recordstore.validation.ValidDateFormat;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "releases", schema = "recordstore")
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "code")
    @NotBlank(message = "Field is mandatory")
    private String code;

    @Column(name = "title")
    @NotBlank(message = "Field is mandatory")
    private String title;

    @Column(name = "release_date")
    @ValidDateFormat
    private String releaseDate;

    @Column(name = "format")
    private Format format;

    @Column(name = "img")
    private String img = "noImageAvailable.png";

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "releases_artists",
            joinColumns = @JoinColumn(name = "release_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private Set<Artist> artists = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "releases_genres",
            joinColumns = @JoinColumn(name = "release_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "label_id")
    private Label label;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "release_id")
    private Set<Track> tracks = new HashSet<>();

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "release_id")
    private Set<YouTubeVideo> videos = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "collection", fetch = FetchType.LAZY)
    private Set<Account> accounts = new HashSet<>();

    // The handling of associations
    public void addArtist(Artist artist){
        this.artists.add(artist);
        artist.getReleases().add(this);
    }
    public void removeArtist(Artist artist){
        this.artists.remove(artist);
        artist.getReleases().remove(this);
    }

    public void addGenre(Genre genre){
        this.genres.add(genre);
        genre.getReleases().add(this);
    }
    public void removeGenre(Genre genre){
        this.genres.remove(genre);
        genre.getReleases().remove(this);
    }

    public void addLabel(Label label){
        this.setLabel(label);
        label.getReleases().add(this);
    }
    public void removeLabel(Label label){
        this.setLabel(null);
        label.getReleases().remove(this);
    }
}