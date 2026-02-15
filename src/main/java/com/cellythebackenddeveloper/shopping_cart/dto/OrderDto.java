package com.cellythebackenddeveloper.shopping_cart.dto;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
private Long id;
private Long userId;
private LocalDateTime orderDate;
private Double totalAmount;
private String status;
private List<OrderItemDto> orderItems;

}
