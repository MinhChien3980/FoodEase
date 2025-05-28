package com.foodease.myapp.service;

import com.foodease.myapp.domain.Favorite;
import com.foodease.myapp.repository.FavoriteRepository;
import com.foodease.myapp.service.dto.request.FavoriteRequest;
import com.foodease.myapp.service.dto.response.FavoriteResponse;
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
