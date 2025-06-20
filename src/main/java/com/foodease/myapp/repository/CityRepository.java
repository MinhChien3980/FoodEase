package com.foodease.myapp.repository;

import com.foodease.myapp.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);
    boolean existsByName(String name);
    Optional<City> findById(Long id);
}
