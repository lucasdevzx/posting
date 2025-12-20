package com.posting.post.mapper;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.config.UserDetailsImpl;
import com.posting.post.dto.request.PostRequestDTO;
import com.posting.post.dto.response.AuthorResponseDTO;
import com.posting.post.dto.response.PermissionsResponseDTO;
import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    private final AuthenticatedUserService authenticatedUserService;

    public PostMapper(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }

    // Recebe entidade e transforma em DTO
    public PostResponseDTO toPost(Post post) {
        UserDetailsImpl currentUser = authenticatedUserService.getCurrentUserDetails();
        return new PostResponseDTO(
                post.getId(),
                new AuthorResponseDTO(
                        post.getUser().getId(),
                        post.getUser().getName()
                ),
                post.getName(),
                post.getDescription(),
                post.getCategorys().stream().findFirst().map(c -> c.getName()).orElse("Uncategorized"),
                post.getDate(),
                new PermissionsResponseDTO(
                        checkEditPermission(post, currentUser),
                        checkDeletePermission(post, currentUser),
                        checkOwnership(post, currentUser)
                )
        );
    }


    // Recebe DTO e transforma em entidade
    public Post toEntity(PostRequestDTO dto) {
        Post post = new Post();
        post.setName(dto.name());
        post.setDescription(dto.description());
        return post;
    }

    public boolean checkEditPermission(Post post, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        return post.getUser().getId().equals(currentUser.getUser().getId());
    }

    public boolean checkDeletePermission(Post post, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
        boolean isOwner = post.getUser().getId().equals(currentUser.getUser().getId());
        return isOwner || isAdmin;
    }

    public boolean checkOwnership(Post post, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        return post.getUser().getId().equals(currentUser.getUser().getId());
    }
}
