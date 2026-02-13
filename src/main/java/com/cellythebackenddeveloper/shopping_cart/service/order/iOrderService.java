package com.cellythebackenddeveloper.shopping_cart.service.order;
import com.cellythebackenddeveloper.shopping_cart.model.Order;

import java.util.List;

public interface iOrderService {

   Order placeOrder(Long userId);

   Order getOrderById(Long orderId);

   List<Order> getOrdersByUserId(Long userId);
}
