package capstone.EpicDelivery.Users;

import capstone.EpicDelivery.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToMany
    private List<Product> products = new ArrayList<>();

    @OneToOne(mappedBy = "cart")
    @JsonBackReference
    private User user;
}