package com.posting.post.repositories;

import com.posting.post.entities.TokenRefresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRefreshRepository extends JpaRepository<TokenRefresh, Long> {

    Optional<TokenRefresh> findByToken(String token);

    void deleteByExpireAtBefore(LocalDateTime createdAt);
}
