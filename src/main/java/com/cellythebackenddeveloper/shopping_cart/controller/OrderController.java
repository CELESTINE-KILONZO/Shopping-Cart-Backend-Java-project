package com.cellythebackenddeveloper.shopping_cart.controller;

import com.cellythebackenddeveloper.shopping_cart.dto.OrderDto;
import com.cellythebackenddeveloper.shopping_cart.exceptions.ResourceNotException;
import com.cellythebackenddeveloper.shopping_cart.model.Order;
import com.cellythebackenddeveloper.shopping_cart.response.ApiResponse;
import com.cellythebackenddeveloper.shopping_cart.service.order.iOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final iOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order created successfully", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occurred!!" + e.getMessage(), null));
        }
    }
@GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse> getOrdersByOrderId(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(new ApiResponse("Orders retrieved successfully", order));
        } catch (ResourceNotException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Ooops!!" + e.getMessage(), null));
        }
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<ApiResponse> getUserOrdersByUserId(@PathVariable Long userId) {
        try {
            List<OrderDto> order = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(new ApiResponse("Orders retrieved successfully", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Ooops!!" + e.getMessage(), null));
        }
    }

}
