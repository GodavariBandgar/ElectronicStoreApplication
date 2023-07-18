package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.entities.Cart;
import com.bikkadit.electronicstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);
}
