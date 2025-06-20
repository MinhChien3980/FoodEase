package com.foodease.myapp.service;

import com.foodease.myapp.domain.*;
import com.foodease.myapp.repository.*;
import com.foodease.myapp.service.dto.request.MenuItemRequest;
import com.foodease.myapp.service.dto.response.MenuItemResponse;
import com.foodease.myapp.service.dto.response.PaginatedMenuItemResponse;
import com.foodease.myapp.service.mapper.MenuItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository repo;
    private final RestaurantRepository restaurantRepo;
    private final MenuCategoryRepository categoryRepo;
    private final MenuItemMapper mapper;

    @Transactional(readOnly=true)
    public List<MenuItemResponse> findByCategory(Long categoryId) {
        return repo.findByCategoryId(categoryId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public List<MenuItemResponse> findByRestaurant(Long restaurantId) {
        return repo.findByRestaurantId(restaurantId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public MenuItemResponse findOne(Long id) {
        MenuItem e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
        return mapper.toDto(e);
    }

    @Transactional
    public MenuItemResponse create(MenuItemRequest dto) {
        Restaurant r = restaurantRepo.findById(dto.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + dto.getRestaurantId()));
        MenuCategory c = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.getCategoryId()));
        MenuItem e = mapper.toEntity(dto);
        e.setRestaurant(r);
        e.setCategory(c);
        return mapper.toDto(repo.save(e));
    }

    @Transactional
    public MenuItemResponse update(Long id, MenuItemRequest dto) {
        MenuItem e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
        Restaurant r = restaurantRepo.findById(dto.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + dto.getRestaurantId()));
        MenuCategory c = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.getCategoryId()));
        mapper.updateFromDto(dto, e);
        e.setRestaurant(r);
        e.setCategory(c);
        return mapper.toDto(repo.save(e));
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MenuItemResponse> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedMenuItemResponse getMenuItemsByRestaurant(
            Long restaurantId, Long categoryId, String search, Integer page, Integer limit) {
        
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<MenuItem> menuItemPage;
        
        if (categoryId != null && search != null && !search.trim().isEmpty()) {
            menuItemPage = repo.findByRestaurantIdAndCategoryIdAndNameContainingIgnoreCase(
                    restaurantId, categoryId, search, pageable);
        } else if (categoryId != null) {
            menuItemPage = repo.findByRestaurantIdAndCategoryId(restaurantId, categoryId, pageable);
        } else if (search != null && !search.trim().isEmpty()) {
            menuItemPage = repo.findByRestaurantIdAndNameContainingIgnoreCase(
                    restaurantId, search, pageable);
        } else {
            menuItemPage = repo.findByRestaurantId(restaurantId, pageable);
        }
        
        List<MenuItemResponse> menuItems = menuItemPage.getContent()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        
        PaginatedMenuItemResponse.Pagination pagination = PaginatedMenuItemResponse.Pagination.builder()
                .currentPage(page)
                .totalPages(menuItemPage.getTotalPages())
                .totalItems(menuItemPage.getTotalElements())
                .itemsPerPage(limit)
                .build();
        
        return PaginatedMenuItemResponse.builder()
                .data(menuItems)
                .pagination(pagination)
                .build();
    }

}
