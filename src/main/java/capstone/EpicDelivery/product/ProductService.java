package capstone.EpicDelivery.product;

import capstone.EpicDelivery.enums.Category;
import capstone.EpicDelivery.exceptions.NotFoundException;
import capstone.EpicDelivery.exceptions.ProductException;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
//        productRepo.save(new Product("Chickenburger","Panino gourmet: pollo fritto croccante, formaggio cheddar, lattuga e pomodoro fresco. Esplosione di sapore in ogni morso!", "https://schaefers-backstuben.de/fileadmin/product_import/sm_8681_crispy-chickenburger.jpg",7.5, Category.HAMBURGER));
//        productRepo.save(new Product("American Burger","Iconico burger americano: carne grigliata, formaggio cheddar, lattuga, pomodoro e cipolla rossa su morbido panino. Sapore autentico USA.", "https://qph.cf2.quoracdn.net/main-qimg-e45b74969769e2579b6b1902494c6204",7.5, Category.HAMBURGER));
//        productRepo.save(new Product("Beef Burger","Festa per gli amanti della carne, con una gustosa patty di manzo grigliata su un morbido panino, arricchita con una varietà di condimenti e salse a scelta. Un'esplosione di sapore di carne di alta qualità che soddisferà ogni palato.", "https://www.hungryjacks.com.au/Upload/HJ/Media/Menu/product/Main/WEB_800x600_ALC_2.png",7.5, Category.PIADINE));
//        productRepo.save(new Product("PeppePollo","La Piadina con pollo e pepperoni offre un'armoniosa combinazione di pollo grigliato tenero e peperoni arrostiti, avvolti in una soffice piadina. Un'accoppiata di sapori deliziosamente saporiti che soddisferà i gusti più raffinati.", "https://www.lapiadineria.com/assets/img/console/mo/products/1098_image_it.png?v=1682512574",7.5, Category.PIADINE));
//        productRepo.save(new Product("AvoPollo","Scopri il Pollorollo con pollo allevato a terra 100% italiano, maionese pomodoro e lattuga. Provalo subito nella versione con avocado per una pausa fresca e leggera.", "https://www.lapiadineria.com/assets/img/console/mo/products/824_image_it.png?v=1682512202",7.5, Category.BEVANDE));
//        productRepo.save(new Product("Coca-Cola","", "https://spesaonline.mdspa.it/milano-asiago/121439-large_default/coca-cola-lattina-cl-50.jpg",7.5, Category.BEVANDE));
//        productRepo.save(new Product("Sprite","", "https://www.blacksheepburger.it/ordina/wp-content/uploads/2018/07/sprite.png",7.5, Category.BEVANDE));
//        productRepo.save(new Product("Fanta","", "https://beedrunk.it/wp-content/uploads/2017/11/fanta-33-cl-sleek.png",7.5, Category.BEVANDE));
//    }

    public Product addItem(Product item) throws ProductException {
        Optional<Product> opt = productRepo.findById(item.getId());
        if(opt.isPresent()) {
            throw new ProductException("Item already exists..");
        }else {
            return productRepo.save(item);
        }
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
        found.setProductName(body.getProductName());
        found.setDescription(body.getDescription());
        found.setPrice(body.getPrice());

        return productRepo.save(found);
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException {
        Product found = this.findById(id);
        productRepo.delete(found);
    }
}
