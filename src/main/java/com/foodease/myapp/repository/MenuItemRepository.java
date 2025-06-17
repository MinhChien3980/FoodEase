package com.foodease.myapp.repository;

import com.foodease.myapp.domain.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoryId(Long categoryId);
    List<MenuItem> findByRestaurantId(Long restaurantId);
    
    // Paginated methods
    Page<MenuItem> findByRestaurantId(Long restaurantId, Pageable pageable);
    Page<MenuItem> findByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId, Pageable pageable);
    Page<MenuItem> findByRestaurantIdAndNameContainingIgnoreCase(Long restaurantId, String search, Pageable pageable);
    Page<MenuItem> findByRestaurantIdAndCategoryIdAndNameContainingIgnoreCase(Long restaurantId, Long categoryId, String search, Pageable pageable);
}
