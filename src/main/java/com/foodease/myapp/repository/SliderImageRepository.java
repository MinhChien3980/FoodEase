package com.foodease.myapp.repository;

import com.foodease.myapp.domain.SliderImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SliderImageRepository extends JpaRepository<SliderImage, Long> {
    List findAllByOrderBySortOrderAsc();
}