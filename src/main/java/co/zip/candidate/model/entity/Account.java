package co.zip.candidate.model.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Describes a user's account on the zip co. platform
 */
@Entity
@Table(name="account")
public class Account extends PersistableEntity {

    /**
     * A single user can have multiple accounts. 
     * 
     * For example. An administrator of the zip co. platform could also be a standard user of the platform.
     * Thus a User could have multiple accounts with different set of roles and permissions.
     */
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    private String username;
    
    private String password;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
