package com.cellythebackenddeveloper.shopping_cart.controller;

import com.cellythebackenddeveloper.shopping_cart.exceptions.ResourceNotException;
import com.cellythebackenddeveloper.shopping_cart.model.Cart;
import com.cellythebackenddeveloper.shopping_cart.response.ApiResponse;
import com.cellythebackenddeveloper.shopping_cart.service.Cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}/getCartById")
    public ResponseEntity <ApiResponse> getCartById(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCartById(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart retrieved successfully", cart));
        } catch (ResourceNotException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/deleteCart")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse( "Cart not found,please check your cart id", null));
        }
    }

    @GetMapping("/{cartId}/totalPrice")
    public ResponseEntity <ApiResponse> getTotalCartPrice(@PathVariable Long cartId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Total cart price retrieved successfully", cartService.getTotalCartPrice(cartId)));
        } catch (ResourceNotException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse( "Cart not found,please check your cart id", null));
        }
    }
}
