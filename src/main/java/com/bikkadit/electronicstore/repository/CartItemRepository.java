package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {


}
