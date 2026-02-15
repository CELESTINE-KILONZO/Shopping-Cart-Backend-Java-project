package com.cellythebackenddeveloper.shopping_cart.repository;
import com.cellythebackenddeveloper.shopping_cart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
