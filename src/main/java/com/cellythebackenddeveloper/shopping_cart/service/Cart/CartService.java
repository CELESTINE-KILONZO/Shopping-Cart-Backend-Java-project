package com.cellythebackenddeveloper.shopping_cart.service.Cart;
import com.cellythebackenddeveloper.shopping_cart.exceptions.ResourceNotException;
import com.cellythebackenddeveloper.shopping_cart.model.Cart;
import com.cellythebackenddeveloper.shopping_cart.repository.CartItemRepository;
import com.cellythebackenddeveloper.shopping_cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor

public class CartService  implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
//    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCartById(Long id) {

        Cart cart = cartRepository.findCartById(id).orElseThrow(() -> new ResourceNotException("Cart not found with id: " + id));
        BigDecimal totalPrice = cart.getTotalAmount();
        cart.setTotalAmount(totalPrice);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepository.findCartById(id).orElseThrow(() -> new ResourceNotException("Cart not found with id: " + id));
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalCartPrice(Long id) {
        Cart cart =getCartById(id);
        return cart.getTotalAmount();
    }

//    @Override
//    public Long initializeNewCart() {
//          Cart newCart = new Cart();
//          Long newCartId = cartIdGenerator.incrementAndGet();
//          newCart.setId(newCartId);
//          return cartRepository.save(newCart).getId();
//      }
@Override
public Long initializeNewCart() {
    Cart newCart = new Cart();
    return cartRepository.save(newCart).getId();
}
}
