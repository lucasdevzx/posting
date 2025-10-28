package com.posting.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ComentResponseDTO(Long comentId,
                                Long userId,
                                Long postId,
                                String name,
                                String coment,

                                @JsonFormat(shape = JsonFormat.Shape.STRING,
                                        pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
                                        timezone = "GMT")
                                LocalDateTime date) {
}