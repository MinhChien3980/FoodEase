package com.foodease.myapp.service;

import com.foodease.myapp.domain.*;
import com.foodease.myapp.repository.*;
import com.foodease.myapp.service.dto.request.MenuCategoryRequest;
import com.foodease.myapp.service.dto.response.CategoryGroupResponse;
import com.foodease.myapp.service.dto.response.MenuCategoryResponse;
import com.foodease.myapp.service.dto.response.RestaurantGroupResponse;
import com.foodease.myapp.service.mapper.MenuCategoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {
    private final MenuCategoryRepository repo;
    private final RestaurantRepository restaurantRepo;
    private final MenuCategoryMapper mapper;

    @Transactional(readOnly=true)
    public List<MenuCategoryResponse> findAll(Long restaurantId) {
        List<MenuCategory> list = repo.findByRestaurantId(restaurantId);
        return list.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public MenuCategoryResponse findOne(Long id) {
        MenuCategory e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + id));
        return mapper.toDto(e);
    }

    @Transactional
    public MenuCategoryResponse create(MenuCategoryRequest dto) {
        // ensure restaurant exists
        Restaurant r = restaurantRepo.findById(dto.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + dto.getRestaurantId()));
        MenuCategory e = mapper.toEntity(dto);
        e.setRestaurant(r);
        return mapper.toDto(repo.save(e));
    }

    @Transactional
    public MenuCategoryResponse update(Long id, MenuCategoryRequest dto) {
        MenuCategory e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + id));
        Restaurant r = restaurantRepo.findById(dto.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + dto.getRestaurantId()));
        mapper.updateFromDto(dto, e);
        e.setRestaurant(r);
        return mapper.toDto(repo.save(e));
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CategoryGroupResponse> findCategoriesGroupedByName() {
        List<MenuCategory> all = repo.findAll();
        // group by category name
        Map<String, List<MenuCategory>> byName = all.stream()
                .collect(Collectors.groupingBy(MenuCategory::getName, Collectors.toList()));

        // build DTO
        return byName.entrySet().stream()
                .map(e -> CategoryGroupResponse.builder()
                        .name(e.getKey())
                        .restaurants(e.getValue().stream()
                                .map(MenuCategory::getRestaurant)
                                .distinct()
                                .map(r -> RestaurantGroupResponse.builder()
                                        .id(r.getId())
                                        .name(r.getName())
                                        .address(r.getAddress())
                                        .build()
                                ).collect(Collectors.toList())
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }


    public List<MenuCategoryResponse> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
