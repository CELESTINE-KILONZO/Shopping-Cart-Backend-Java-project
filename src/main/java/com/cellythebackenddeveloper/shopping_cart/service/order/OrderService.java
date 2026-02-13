package com.cellythebackenddeveloper.shopping_cart.service.order;
import com.cellythebackenddeveloper.shopping_cart.enums.OrderStatus;
import com.cellythebackenddeveloper.shopping_cart.exceptions.ResourceNotException;
import com.cellythebackenddeveloper.shopping_cart.model.Cart;
import com.cellythebackenddeveloper.shopping_cart.model.Order;
import com.cellythebackenddeveloper.shopping_cart.model.OrderItem;
import com.cellythebackenddeveloper.shopping_cart.model.Product;
import com.cellythebackenddeveloper.shopping_cart.repository.OrderRepository;
import com.cellythebackenddeveloper.shopping_cart.repository.ProductRepository;
import com.cellythebackenddeveloper.shopping_cart.service.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor

public class OrderService implements iOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order , Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
        return new OrderItem(
                order,
                product,
                cartItem.getQuantity(),
                cartItem.getUnitPrice()
        );
    }).toList();
   }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply( new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotException("Order not found"));
    }
}
