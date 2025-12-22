package com.posting.post.services;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.dto.request.PostRequestDTO;
import com.posting.post.entities.User;
import com.posting.post.mapper.PostMapper;
import com.posting.post.services.exceptions.ResourceNotFoundException;
import com.posting.post.services.exceptions.UnauthorizedActionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.posting.post.entities.Post;
import com.posting.post.repositories.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final UserService userService;
    private final AuthenticatedUserService authenticatedUserService;

    public PostService(PostRepository postRepository,
                       PostMapper postMapper,
                       UserService userService,
                       AuthenticatedUserService authenticatedUserService) {

        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userService = userService;
        this.authenticatedUserService = authenticatedUserService;
    }

    public Page<Post> findAll(int page, int size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, size));

        // Regra de negócio
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException(posts);
        }
        return posts;
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(id));
    }

    @PreAuthorize("isAuthenticated()")
    public Page<Post> findPostsByUserId(int page, int size) {
        User user = authenticatedUserService.getCurrentUser();
        Page<Post> posts = postRepository.findByUser_Id(user.getId(), PageRequest.of(page, size));

        // Regra de negócio
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException(posts);
        }

        return posts;
    }

    @PreAuthorize("isAuthenticated()")
    public Post createPost(PostRequestDTO dto) {
        User user = authenticatedUserService.getCurrentUser();
        Post post = postMapper.toEntity(dto);
        post.setUser(user);
        return postRepository.save(post);
    }

    @PreAuthorize("isAuthenticated()")
    public Post updatePost(Long postId, PostRequestDTO dto) {
        Long userId = authenticatedUserService.getCurrentUserId();
        // Regra de negócio
        boolean exists = postRepository.existsByIdAndUser_Id(postId, userId);

        if (!exists) throw new UnauthorizedActionException(userId);

        Post entity = postRepository.getReferenceById(postId);
        Post obj = postMapper.toEntity(dto);
        updateData(entity, obj);
        return postRepository.save(entity);
    }

    // Auxílio Update
    public void updateData(Post entity, Post obj) {
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
    }

    @PreAuthorize("isAuthenticated()")
    public void deletePost(Long postId) {
        Long userId = authenticatedUserService.getCurrentUserId();
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException(postId));

        // Regra de negócio
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
        boolean isOwner = post.getUser().getId().equals(userId);

        if (!isOwner && !isAdmin) throw new UnauthorizedActionException(userId);

        postRepository.delete(post);
    }
}