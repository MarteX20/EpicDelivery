package capstone.EpicDelivery.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItem> getCartItems() {
        return cartService.getCartItems();
    }

    @PostMapping
    public void addItemToCart(@RequestBody CartItem item) {
        cartService.addItemToCart(item);
    }

    @DeleteMapping("/{itemId}")
    public void removeItemFromCart(@PathVariable UUID itemId) {
        cartService.removeItemFromCart(itemId);
    }

    @PutMapping("/{itemId}")
    public void updateItemInCart(@PathVariable UUID itemId, @RequestBody CartItem updatedItem) {
        cartService.updateItemInCart(itemId, updatedItem);
    }
}
