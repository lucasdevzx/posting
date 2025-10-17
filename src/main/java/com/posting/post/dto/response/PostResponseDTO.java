package com.posting.post.dto.response;

import com.posting.post.entities.Post;

import java.time.LocalDate;
import java.util.List;

public record PostResponseDTO(String name, String description, LocalDate date) {
}
