package be.d2l.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="comments")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    private int rating;
    private Date creationDate;
    private Boolean isDeleted;
    private int idUser;
    private int idProduct;

    public Comment() {}

    public boolean checkValidity() {
        return text != null && !text.isEmpty() && !text.isBlank()
                && rating >= 0 && rating <= 5 && idUser >= 0 && idProduct >= 0;
    }
}
