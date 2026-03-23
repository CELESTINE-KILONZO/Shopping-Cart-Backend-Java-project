package com.cellythebackenddeveloper.shopping_cart.controller;

import com.cellythebackenddeveloper.shopping_cart.exceptions.ResourceNotException;
import com.cellythebackenddeveloper.shopping_cart.model.Cart;
import com.cellythebackenddeveloper.shopping_cart.model.User;
import com.cellythebackenddeveloper.shopping_cart.response.ApiResponse;
import com.cellythebackenddeveloper.shopping_cart.service.Cart.ICartItemService;
import com.cellythebackenddeveloper.shopping_cart.service.Cart.ICartService;
import com.cellythebackenddeveloper.shopping_cart.service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService iUserService;

    @PostMapping("/addItemToCart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId, @RequestParam int quantity) {
        try {
            User user = iUserService.getUserById(1L);
            Cart cart = cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item added to cart successfully", null));
        } catch (ResourceNotException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

@DeleteMapping("/cartId/{cartId}/productId/{productId}/removeItemFromCart")
public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
    try {
        cartItemService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok(new ApiResponse("Item removed from cart successfully", null));
    } catch (ResourceNotException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
}

@PutMapping("/cart/{cartId}/item/{productId}/updateItemQuantity")
public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
    try {
        cartItemService.updateItemQuantity(cartId, productId, quantity);
        System.out.println("THe quantity before update is: " + quantity);
        return ResponseEntity.ok(new ApiResponse("Item quantity updated successfully", null));
    } catch (ResourceNotException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
}

@GetMapping("/getCartItem")
public ResponseEntity<ApiResponse> getCartItem(@RequestParam Long cartId, @RequestParam Long productId) {
    try {
        return ResponseEntity.ok(new ApiResponse("Item fetched successfully", cartItemService.getCartItem(cartId, productId)));
    } catch (ResourceNotException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
}
}
