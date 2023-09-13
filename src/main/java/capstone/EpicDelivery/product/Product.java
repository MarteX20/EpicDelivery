package capstone.EpicDelivery.product;

import capstone.EpicDelivery.enums.Category;
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

    @Enumerated(EnumType.STRING)
    private Category category;

    public Product(String name, String description, String img,double price, Category category) {
        this.productName = name;
        this.price = price;
        this.description = description;
        this.img = img;
        this.category = category;
    }
}
