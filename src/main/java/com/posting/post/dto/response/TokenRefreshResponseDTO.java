package com.posting.post.dto.response;

public record TokenRefreshResponseDTO(
        String tokenAcess,
        String tokenRefresh
) {
}
