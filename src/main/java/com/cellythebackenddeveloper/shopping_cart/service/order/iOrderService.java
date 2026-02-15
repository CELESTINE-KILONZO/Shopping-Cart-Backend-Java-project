package com.cellythebackenddeveloper.shopping_cart.service.order;
import com.cellythebackenddeveloper.shopping_cart.dto.OrderDto;
import com.cellythebackenddeveloper.shopping_cart.model.Order;

import java.util.List;

public interface iOrderService {

   Order placeOrder(Long userId);

   OrderDto getOrderById(Long orderId);

   List<OrderDto> getOrdersByUserId(Long userId);
}
