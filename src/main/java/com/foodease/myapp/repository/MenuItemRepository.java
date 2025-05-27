package com.foodease.myapp.repository;

import com.foodease.myapp.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurant_Id(long restaurantId);

    List<MenuItem> findByCategory_Id(long categoryId);

    List<MenuItem> findByRestaurant_IdAndCategory_Id(long restaurantId, long categoryId);
}
