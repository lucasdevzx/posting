package com.posting.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PostRequestDTO(

        @NotBlank(message = "{PostRequestDTO.name.NotBlank}")
        String name,

        @Size(min = 0, max = 2200, message = "{PostRequestDTO.description.Size}")
        String description){}