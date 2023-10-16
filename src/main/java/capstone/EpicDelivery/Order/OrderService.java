package capstone.EpicDelivery.Order;

import capstone.EpicDelivery.Users.User;
import capstone.EpicDelivery.cart.Cart;
import capstone.EpicDelivery.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final CartService cartService;

    @Autowired
    public OrderService(CartService cartService) {
        this.cartService = cartService;
    }

    public void placeOrder(User user) {
        Cart cart = user.getCart();
        cartService.checkout(cart);
    }
}
