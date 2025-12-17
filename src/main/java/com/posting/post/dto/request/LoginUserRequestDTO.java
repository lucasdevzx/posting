package com.posting.post.dto.request;

public record LoginUserRequestDTO(
        String email,
        String password
) {
}
