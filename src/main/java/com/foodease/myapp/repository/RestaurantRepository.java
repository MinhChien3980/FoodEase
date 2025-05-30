package com.foodease.myapp.repository;

import com.foodease.myapp.domain.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByNameContainingIgnoreCase(String q);

    @EntityGraph(attributePaths = "menuItems")
    @Query("select r from Restaurant r")
    List<Restaurant> findAllWithMenuItems();

    @EntityGraph(attributePaths = {"menuItems", "menuItems.category"})
    List<Restaurant> findAll();
}
