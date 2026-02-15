package com.posting.post.entities;

import com.posting.post.enums.TokenRefreshEnums;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "tb_token_refresh")
@Entity
public class TokenRefresh  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    User user;

    String token;

    @CreatedDate
    LocalDateTime expireAt;

    @Enumerated(EnumType.STRING)
    TokenRefreshEnums status;

    public TokenRefresh() {}

    public TokenRefresh(User user, String token, LocalDateTime expireAt, TokenRefreshEnums status) {
        this.user = user;
        this.token = token;
        this.expireAt = expireAt;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public TokenRefreshEnums getStatus() {
        return status;
    }

    public void setStatus(TokenRefreshEnums status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TokenRefresh that = (TokenRefresh) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}