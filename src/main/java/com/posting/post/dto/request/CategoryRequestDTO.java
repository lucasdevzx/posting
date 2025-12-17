package com.posting.post.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(

        @NotBlank(message = "{CategoryRequestDTO.title.NotBlank}")
        String name) {
}