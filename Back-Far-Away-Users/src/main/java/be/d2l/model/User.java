package be.d2l.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String surname;
    private String firstName;
    private String nickname;
    private Date birthDate;
    private String address;
    private String mail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public User() {}

    public boolean checkUserFields() {
        return this.getMail() != null && !this.getMail().isBlank() && !this.getMail().isEmpty()
                && this.getPassword() != null && !this.getPassword().isEmpty() && !this.getPassword().isBlank();
    }

}
