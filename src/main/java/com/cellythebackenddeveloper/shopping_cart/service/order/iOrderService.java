package com.cellythebackenddeveloper.shopping_cart.service.order;
import com.cellythebackenddeveloper.shopping_cart.model.Order;

public interface iOrderService {
   Order createOrder(Long userId);
   Order getOrderById(Long orderId);
}
