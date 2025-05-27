package com.foodease.myapp.repository;

import com.foodease.myapp.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurantId(Long restaurantId);

    List<MenuItem> findByCategoryId(Long categoryId);
}
