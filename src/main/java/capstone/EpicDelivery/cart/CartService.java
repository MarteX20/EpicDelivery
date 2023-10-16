package capstone.EpicDelivery.cart;

import capstone.EpicDelivery.Order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    private List<CartItem> cartItems = new ArrayList<>();

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addItemToCart(CartItem item) {
        cartItems.add(item);
    }

    public void updateItemInCart(UUID itemId, CartItem updatedItem) {
        CartItem existingItem = cartRepository.findById(itemId).orElse(null);

        if (existingItem != null) {

            existingItem.setImg(updatedItem.getImg());
            existingItem.setProductName(updatedItem.getProductName());
            existingItem.setPrice(updatedItem.getPrice());
            existingItem.setQuantity(updatedItem.getQuantity());


            cartRepository.save(existingItem);
        }
    }

    public void removeItemFromCart(UUID itemId) {
        cartRepository.deleteById(itemId);
    }

    public void checkout(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setProducts(new ArrayList<>(cart.getProducts()));

        // Aggiungi l'ordine alla storia dell'utente
        cart.getUser().getOrderHistory().add(order);

        // Opzionale: svuotare il carrello dopo l'ordine
        cart.getProducts().clear();
    }
}

