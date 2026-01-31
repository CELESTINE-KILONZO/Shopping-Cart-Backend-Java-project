package com.cellythebackenddeveloper.shopping_cart.service.Cart;

import com.cellythebackenddeveloper.shopping_cart.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long id, Long productId);
}
