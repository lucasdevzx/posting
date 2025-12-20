package com.posting.post.dto.request;

public record RegisterUserRequestDTO(
        String name,
        String email,
        String password,
        String role
) {
}
