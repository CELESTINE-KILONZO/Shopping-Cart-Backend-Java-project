package com.cellythebackenddeveloper.shopping_cart.dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long itemId;
    private int quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
