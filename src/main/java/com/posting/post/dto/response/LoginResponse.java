package com.posting.post.dto.response;

import java.time.Instant;

public record LoginResponse(

        String token,
        Instant expiresIn

) {
}
