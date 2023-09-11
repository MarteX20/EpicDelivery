package capstone.EpicDelivery.product;

import capstone.EpicDelivery.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    @Column(length = 550)
    private String description;
    private String img;
    private double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Product(String name, String description, String img,double price, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.img = img;
        this.category = category;
    }

    public Product(String name, String description, String img, double price) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.img = img;
    }
}
