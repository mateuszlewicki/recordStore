package recordstore.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "recordstore")
public class Role implements GrantedAuthority {

    @Id
    private long id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts;

    public Role() {
    }

    public Role(long id) {
        this.id = id;
    }

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}