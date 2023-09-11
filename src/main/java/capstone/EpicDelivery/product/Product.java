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
    private String description;
    private double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Product(String name, String description, double price) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
