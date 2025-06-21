package com.foodease.myapp.repository;

import com.foodease.myapp.domain.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    List<MenuCategory> findByRestaurantId(Long restaurantId);
}
