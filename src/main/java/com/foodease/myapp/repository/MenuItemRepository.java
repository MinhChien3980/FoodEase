package com.foodease.myapp.repository;

import com.foodease.myapp.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoryId(Long categoryId);
    List<MenuItem> findByRestaurantId(Long restaurantId);
}
