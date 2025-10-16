package com.posting.post.dto.request;

public record UserRequestDTO(
        String name,
        String email,
        String password
) {
}
