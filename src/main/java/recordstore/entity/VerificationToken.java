package recordstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tokens", schema = "recordstore")
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "token")
    private String token = generateToken();

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    private Account account;

    private Date expiryDate = calculateExpiryDate();

    public void updateToken() {
        this.token = generateToken();
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, VerificationToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}