package capstone.EpicDelivery.product;

import capstone.EpicDelivery.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    private String productName;

    private String description;
    private String img;
    private double price;
    private int quantity;

    @Embedded
    @OneToOne(cascade = CascadeType.ALL)
    private Category category;

    public Product(String name, String description, String img,double price, Category category) {
        this.productName = name;
        this.price = price;
        this.description = description;
        this.img = img;
        this.category = category;
    }

    public Product(String name, String description, String img, double price) {
        this.productName = name;
        this.price = price;
        this.description = description;
        this.img = img;
    }
}
