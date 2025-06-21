package com.foodease.myapp.repository;

import com.foodease.myapp.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserIdAndFavoritableType(Long userId, String type);
    Optional<Favorite> findByUserIdAndFavoritableTypeAndFavoritableId(Long userId, String type, Long id);
    void deleteByUserIdAndFavoritableTypeAndFavoritableId(Long userId, String type, Long id);
}
