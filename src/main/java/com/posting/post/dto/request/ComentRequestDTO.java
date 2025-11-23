package com.posting.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ComentRequestDTO(

        @NotBlank(message = "{ComentRequestDTO.coment.NotBlank}")
        @Size(min = 0, max = 2200, message = "{ComentRequestDTO.coment.Size}")
        String coment) {}
