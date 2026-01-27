package com.cellythebackenddeveloper.shopping_cart.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private BigDecimal totalAmount;
    private Set<CartItem> items;
}
