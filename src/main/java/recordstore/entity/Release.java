package recordstore.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Entity
@Table(name = "releases", schema = "recordstore")
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "artist")
    @NotBlank(message = "Field is mandatory")
    private String artist;

    @Column(name = "title")
    @NotBlank(message = "Field is mandatory")
    private String title;

    @Column(name = "label")
    @NotBlank(message = "Field is mandatory")
    private String label;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "genre")
    @NotBlank(message = "Field is mandatory")
    private String genre;

    @Column(name = "format")
    @NotBlank(message = "Field is mandatory")
    private String format;

    @Column(name = "price")
    private double price;

    @Column(name = "img")
    @NotBlank(message = "Field is mandatory")
    private String img;

    @Column(name = "quantity")
    private int quantity;

    public Release(){
    }

    public Release(String artist, String title, String label, Date releaseDate, String genre, String code, String format, double price, String img, int quantity) {
        this.artist = artist;
        this.title = title;
        this.label = label;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.code = code;
        this.format = format;
        this.price = price;
        this.img = img;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
       this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Release release = (Release) o;

        return getId() == release.getId();

    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Release{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", label='" + label + '\'' +
                ", releaseDate=" + releaseDate +
                ", genre='" + genre + '\'' +
                ", format='" + format + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}