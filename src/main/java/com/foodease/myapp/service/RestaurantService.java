package com.foodease.myapp.service;

import com.foodease.myapp.domain.Restaurant;
import com.foodease.myapp.domain.User;
import com.foodease.myapp.repository.RestaurantRepository;
import com.foodease.myapp.repository.UserRepository;
import com.foodease.myapp.service.dto.request.RestaurantRequest;
import com.foodease.myapp.service.dto.response.MenuItemSummary;
import com.foodease.myapp.service.dto.response.RestaurantResponse;
import com.foodease.myapp.service.dto.response.RestaurantWithItemsResponse;
import com.foodease.myapp.service.mapper.RestaurantMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repo;
    private final UserRepository userRepo;
    private final RestaurantMapper mapper;

    @Transactional(readOnly=true)
    public List<RestaurantResponse> findAll(String search) {
        List<Restaurant> list = (search == null || search.isBlank())
                ? repo.findAll()
                : repo.findByNameContainingIgnoreCase(search);
        return list.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public RestaurantResponse findOne(Long id) {
        Restaurant e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + id));
        return mapper.toDto(e);
    }

    @Transactional
    public RestaurantResponse create(RestaurantRequest dto) {
        // ensure owner exists
        User owner = userRepo.findById(dto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getOwnerId()));
        Restaurant r = mapper.toEntity(dto);
        r.setOwner(owner);
        return mapper.toDto(repo.save(r));
    }

    @Transactional
    public RestaurantResponse update(Long id, RestaurantRequest dto) {
        Restaurant r = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + id));
        User owner = userRepo.findById(dto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getOwnerId()));
        mapper.updateFromDto(dto, r);
        r.setOwner(owner);
        return mapper.toDto(repo.save(r));
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RestaurantWithItemsResponse> findAllWithItems() {
        return repo.findAllWithMenuItems().stream()
                .map(r -> RestaurantWithItemsResponse.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .address(r.getAddress())
                        .menuItems(r.getMenuItems().stream()
                                .map(mi -> MenuItemSummary.builder()
                                        .id(mi.getId())
                                        .name(mi.getName())
                                        .description(mi.getDescription())
                                        .price(mi.getPrice())
                                        .imageUrl(mi.getImageUrl())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RestaurantWithItemsResponse> getAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private RestaurantWithItemsResponse toDto(Restaurant r) {
        List<MenuItemSummary> items = r.getMenuItems().stream().map(mi ->
                MenuItemSummary.builder()
                        .id(mi.getId())
                        .name(mi.getName())
                        .description(mi.getDescription())
                        .price(mi.getPrice())
                        .imageUrl(mi.getImageUrl())
                        .categoryId(mi.getCategory() != null ? mi.getCategory().getId() : null)
                        .categoryName(mi.getCategory() != null ? mi.getCategory().getName() : null)
                        .build()
        ).collect(Collectors.toList());

        return RestaurantWithItemsResponse.builder()
                .id(r.getId())
                .name(r.getName())
                .address(r.getAddress())
                .menuItems(items)
                .build();
    }
}
