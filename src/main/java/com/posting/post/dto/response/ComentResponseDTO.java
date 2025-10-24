package com.posting.post.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ComentResponseDTO(String name, String coment, LocalDateTime date) {
}