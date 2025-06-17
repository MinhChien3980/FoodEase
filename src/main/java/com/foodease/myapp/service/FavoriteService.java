package com.foodease.myapp.service;

import com.foodease.myapp.domain.Favorite;
import com.foodease.myapp.repository.FavoriteRepository;
import com.foodease.myapp.service.dto.request.FavoriteRequest;
import com.foodease.myapp.service.dto.request.ToggleFavoriteRequest;
import com.foodease.myapp.service.dto.response.FavoriteResponse;
import com.foodease.myapp.service.dto.response.ToggleFavoriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository repo;

    @Transactional
    public FavoriteResponse addFavorite(FavoriteRequest req) {
        // prevent duplicates
        repo.findByUserIdAndFavoritableTypeAndFavoritableId(
                req.getUserId(), req.getFavoritableType(), req.getFavoritableId()
        ).ifPresent(f -> {
            throw new IllegalStateException("Already a favorite");
        });

        Favorite fav = Favorite.builder()
                .userId(req.getUserId())
                .favoritableType(req.getFavoritableType())
                .favoritableId(req.getFavoritableId())
                .createdAt(LocalDateTime.now())
                .build();

        Favorite saved = repo.save(fav);
        return toDto(saved);
    }

    @Transactional
    public void removeFavorite(FavoriteRequest req) {
        repo.deleteByUserIdAndFavoritableTypeAndFavoritableId(
                req.getUserId(), req.getFavoritableType(), req.getFavoritableId());
    }

    @Transactional(readOnly=true)
    public List<FavoriteResponse> listFavorites(Long userId, String type) {
        return repo.findByUserIdAndFavoritableType(userId, type).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ToggleFavoriteResponse toggleFavorite(ToggleFavoriteRequest request) {
        // For now, assume we get user ID from authentication context
        // In real implementation, extract from JWT token
        Long userId = getCurrentUserId(); // This needs to be implemented
        String favoritableType = "menu_item"; // Since we're dealing with menu items
        
        // Check if favorite already exists
        var existingFavorite = repo.findByUserIdAndFavoritableTypeAndFavoritableId(
                userId, favoritableType, request.getMenuItemId());
        
        boolean isFavorite;
        if (existingFavorite.isPresent()) {
            // Remove from favorites
            repo.delete(existingFavorite.get());
            isFavorite = false;
        } else {
            // Add to favorites
            Favorite favorite = Favorite.builder()
                    .userId(userId)
                    .favoritableType(favoritableType)
                    .favoritableId(request.getMenuItemId())
                    .createdAt(LocalDateTime.now())
                    .build();
            repo.save(favorite);
            isFavorite = true;
        }
        
        return ToggleFavoriteResponse.builder()
                .menuItemId(request.getMenuItemId())
                .isFavorite(isFavorite)
                .build();
    }
    
    // This method needs to be implemented based on your authentication system
    private Long getCurrentUserId() {
        // For now, return a dummy user ID
        // In real implementation, extract from JWT token or Spring Security context
        return 1L; // TODO: Implement proper user ID retrieval
    }

    private FavoriteResponse toDto(Favorite f) {
        return FavoriteResponse.builder()
                .id(f.getId())
                .userId(f.getUserId())
                .favoritableType(f.getFavoritableType())
                .favoritableId(f.getFavoritableId())
                .createdAt(f.getCreatedAt())
                .build();
    }
}
