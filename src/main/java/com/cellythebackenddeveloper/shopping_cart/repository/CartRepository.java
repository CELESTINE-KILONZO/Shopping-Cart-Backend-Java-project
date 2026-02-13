package com.cellythebackenddeveloper.shopping_cart.repository;
import com.cellythebackenddeveloper.shopping_cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartById(Long id);

    List<Cart> id(Long id);

    Optional<Cart> findCartByUserId (Long userId);
}
