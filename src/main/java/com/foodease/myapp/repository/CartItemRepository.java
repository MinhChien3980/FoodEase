package com.foodease.myapp.repository;

import com.foodease.myapp.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);
    List<CartItem> findByMenuItemId(Long menuItemId);
    void deleteByCartId(Long cartId);
} 