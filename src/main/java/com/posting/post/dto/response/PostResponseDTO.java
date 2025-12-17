package com.posting.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.posting.post.entities.Category;

import java.time.LocalDateTime;

public record PostResponseDTO(
        Long id,
        AuthorResponseDTO author,
        String title,
        String description,
        String category,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime date,

        PermissionsResponseDTO permissions
        ) {
}
