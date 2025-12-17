package com.posting.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ComentResponseDTO(Long id,
                                String comment,
                                AuthorResponseDTO author,
                                Long postId,

                                @JsonFormat(shape = JsonFormat.Shape.STRING,
                                        pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
                                        timezone = "GMT")
                                LocalDateTime date,

                                PermissionsResponseDTO permissions) {
}