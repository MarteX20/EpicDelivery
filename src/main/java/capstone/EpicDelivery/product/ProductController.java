package capstone.EpicDelivery.product;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productServ) {
        this.productService = productServ;
    }

    @GetMapping
    public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "14") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return productService.find(page, size, sortBy);
    }

    @GetMapping("/{productId}")
    public Product findById(@PathVariable UUID productId) {
        return productService.findById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Product createProduct(@Valid @RequestBody Product body) {
        return productService.create(body);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Product updateProduct(@PathVariable UUID productId, @RequestBody Product body) {
        return productService.findByIdAndUpdate(productId, body);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID productId) {
        productService.findByIdAndDelete(productId);
    }
}
