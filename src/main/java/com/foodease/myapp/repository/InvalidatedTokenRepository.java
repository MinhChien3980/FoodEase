package com.foodease.myapp.repository;

import com.foodease.myapp.domain.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

/**
 * Repository for managing invalidated JWT tokens (JTI blacklist).
 *
 * The ID type is the token JTI (String).
 */
@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    boolean existsByToken(String token);
    long deleteByExpiryTimeBefore(Instant cutoff);
}

