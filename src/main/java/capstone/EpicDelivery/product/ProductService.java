package capstone.EpicDelivery.product;


import capstone.EpicDelivery.enums.Category;
import capstone.EpicDelivery.exceptions.NotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepository) {
        this.productRepo = productRepository;
    }

//    @PostConstruct
//    public void init() {
//        productRepo.save(new Product("Chickenburger", 7.5, Category.HAMBURGER));
//        productRepo.save(new Product("American Burger", 8.5, Category.HAMBURGER));
//        productRepo.save(new Product("BBQ Burger", 9.5, Category.HAMBURGER));
//        productRepo.save(new Product("Pepperoni Pollo", 7.5, Category.PIADINE));
//        productRepo.save(new Product("Pollo BBQ", 7.0, Category.PIADINE));
//        productRepo.save(new Product("Cola", 1.5, Category.BEVANDE));
//        productRepo.save(new Product("Spite", 1.5, Category.BEVANDE));
//        productRepo.save(new Product("Fata", 1.5, Category.BEVANDE));
//    }

    public Product create(Product body) {
        Product newProduct = new Product(body.getName(), body.getDescription(), body.getPrice());
        return productRepo.save(newProduct);
    }

    public Page<Product> find(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return productRepo.findAll(pageable);
    }

    public Product findById(UUID id) throws NotFoundException {
        return productRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Product findByIdAndUpdate(UUID id, Product body) throws NotFoundException {
        Product found = this.findById(id);
        found.setName(body.getName());
        found.setDescription(body.getDescription());
        found.setPrice(body.getPrice());

        return productRepo.save(found);
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException {
        Product found = this.findById(id);
        productRepo.delete(found);
    }
}
