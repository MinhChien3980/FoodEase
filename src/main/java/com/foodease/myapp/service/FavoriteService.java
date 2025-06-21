package com.foodease.myapp.service;

import com.foodease.myapp.domain.Favorite;
import com.foodease.myapp.domain.MenuItem;
import com.foodease.myapp.domain.Restaurant;
import com.foodease.myapp.domain.User;
import com.foodease.myapp.exception.AppException;
import com.foodease.myapp.exception.ErrorCode;
import com.foodease.myapp.repository.FavoriteRepository;
import com.foodease.myapp.repository.MenuItemRepository;
import com.foodease.myapp.repository.RestaurantRepository;
import com.foodease.myapp.repository.UserRepository;
import com.foodease.myapp.service.dto.request.FavoriteRequest;
import com.foodease.myapp.service.dto.response.MenuItemResponse;
import com.foodease.myapp.service.dto.response.RestaurantResponse;
import com.foodease.myapp.service.dto.response.ToggleFavoriteResponse;
import com.foodease.myapp.service.mapper.MenuItemMapper;
import com.foodease.myapp.service.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantMapper restaurantMapper;
    private final MenuItemMapper menuItemMapper;

    @Transactional
    public ToggleFavoriteResponse toggleFavorite(FavoriteRequest request) {
        User user = getCurrentUser();
        String favoritableType = request.getFavoritableType();
        Long favoritableId = request.getFavoritableId();

        validateFavoritable(favoritableType, favoritableId);

        Optional<Favorite> existingFavorite = favoriteRepository.findByUserIdAndFavoritableTypeAndFavoritableId(
                user.getId(), favoritableType, favoritableId);

        boolean isFavorite;
        if (existingFavorite.isPresent()) {
            favoriteRepository.delete(existingFavorite.get());
            isFavorite = false;
        } else {
            Favorite favorite = Favorite.builder()
                    .userId(user.getId())
                    .favoritableType(favoritableType)
                    .favoritableId(favoritableId)
                    .createdAt(LocalDateTime.now())
                    .build();
            favoriteRepository.save(favorite);
            isFavorite = true;
        }

        return ToggleFavoriteResponse.builder()
                .favoritableId(favoritableId)
                .favoritableType(favoritableType)
                .isFavorite(isFavorite)
                .build();
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getFavorites() {
        User user = getCurrentUser();
        Map<String, Object> favoritesMap = new HashMap<>();

        List<Favorite> restaurantFavorites = favoriteRepository.findByUserIdAndFavoritableType(user.getId(), "restaurant");
        if(restaurantFavorites != null && !restaurantFavorites.isEmpty()){
            List<Long> restaurantIds = restaurantFavorites.stream().map(Favorite::getFavoritableId).collect(Collectors.toList());
            List<Restaurant> restaurants = restaurantRepository.findAllById(restaurantIds);
            List<RestaurantResponse> restaurantResponses = restaurants.stream()
                    .map(restaurantMapper::toDto)
                    .collect(Collectors.toList());
            favoritesMap.put("restaurants", restaurantResponses);
        }


        List<Favorite> menuItemFavorites = favoriteRepository.findByUserIdAndFavoritableType(user.getId(), "menu_item");
        if(menuItemFavorites != null && !menuItemFavorites.isEmpty()){
            List<Long> menuItemIds = menuItemFavorites.stream().map(Favorite::getFavoritableId).collect(Collectors.toList());
            List<MenuItem> menuItems = menuItemRepository.findAllById(menuItemIds);
            List<MenuItemResponse> menuItemResponses = menuItems.stream()
                    .map(menuItemMapper::toDto)
                    .collect(Collectors.toList());
            favoritesMap.put("menu_items", menuItemResponses);
        }


        return favoritesMap;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void validateFavoritable(String type, Long id) {
        if ("restaurant".equalsIgnoreCase(type)) {
            if (!restaurantRepository.existsById(id)) {
                throw new AppException(ErrorCode.RESTAURANT_NOT_FOUND);
            }
        } else if ("menu_item".equalsIgnoreCase(type)) {
            if (!menuItemRepository.existsById(id)) {
                throw new AppException(ErrorCode.MENU_ITEM_NOT_FOUND);
            }
        } else {
            throw new AppException(ErrorCode.INVALID_FAVORITE_TYPE);
        }
    }
} 