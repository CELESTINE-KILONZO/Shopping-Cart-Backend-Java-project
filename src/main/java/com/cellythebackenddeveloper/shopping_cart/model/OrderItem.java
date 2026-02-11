package com.cellythebackenddeveloper.shopping_cart.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor

public class OrderItem {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
   @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Order order, Product product, int quantity, BigDecimal price) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.order = order;
    }
}
