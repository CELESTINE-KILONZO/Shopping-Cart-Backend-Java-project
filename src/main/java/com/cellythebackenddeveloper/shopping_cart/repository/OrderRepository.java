package com.cellythebackenddeveloper.shopping_cart.repository;
import com.cellythebackenddeveloper.shopping_cart.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
