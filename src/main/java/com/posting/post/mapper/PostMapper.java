package com.posting.post.mapper;

import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.entities.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    public List<PostResponseDTO> toPost(List<Post> posts) {
        return posts.stream()
                .map(post -> {
                    return new PostResponseDTO(
                            post.getName(),
                            post.getDescription(),
                            post.getDate());
                }).collect(Collectors.toList());
    }
}
