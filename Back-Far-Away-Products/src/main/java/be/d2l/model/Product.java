package be.d2l.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String name;
    private String shortDescription;
    private String detailedDescription;
    private float price;
    private String category;

    public Product(){
    }

    public Product(int id, String name, String shortDescription, String detailedDescription, float price, String category) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.price = price;
        this.category = category;
    }
}
