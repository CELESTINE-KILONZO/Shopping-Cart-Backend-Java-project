package com.cellythebackenddeveloper.shopping_cart.service.Cart;
import com.cellythebackenddeveloper.shopping_cart.model.Cart;
import java.math.BigDecimal;

public interface ICartService {

    Cart getCartById(Long id);
    void clearCart(Long id);
    BigDecimal getTotalCartPrice(Long id);
}
