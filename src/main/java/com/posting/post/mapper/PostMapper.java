package com.posting.post.mapper;

import com.posting.post.dto.request.PostRequestDTO;
import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    // Recebe lista de entidades e transforma em lista de DTOs
    public List<PostResponseDTO> toPosts(List<Post> posts) {
        return posts.stream()
                .map(post -> {
                    return new PostResponseDTO(
                            post.getId(),
                            post.getUser().getName(),
                            post.getName(),
                            post.getDescription(),
                            post.getDate());
                }).collect(Collectors.toList());
    }

    // Recebe entidade e transforma em DTO
    public PostResponseDTO toPost(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getUser().getName(),
                post.getName(),
                post.getDescription(),
                post.getDate()
        );
    }

    // Recebe DTO e transforma em entidade
    public Post toEntity(PostRequestDTO dto) {
        Post post = new Post();
        post.setName(dto.name());
        post.setDescription(dto.description());
        return post;
    }
}
