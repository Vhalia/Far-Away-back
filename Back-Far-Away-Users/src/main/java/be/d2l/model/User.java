package be.d2l.model;

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
    private String password;
    private boolean admin;

    public User() {}

}
