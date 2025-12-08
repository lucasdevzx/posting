package com.posting.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.posting.post.entities.Post;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDTO(
        Long id,
        Long userId,
        String author,
        String name,
        String description,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        LocalDateTime date) {
}
