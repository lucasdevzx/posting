package com.posting.post.services;

import java.util.List;

import com.posting.post.dto.request.PostRequestDTO;
import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.dto.response.UserResponseDTO;
import com.posting.post.entities.User;
import com.posting.post.mapper.PostMapper;
import com.posting.post.mapper.UserMapper;
import com.posting.post.services.exceptions.ResourceNotFoundException;
import com.posting.post.services.exceptions.UnauthorizedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.posting.post.entities.Post;
import com.posting.post.repositories.PostRepository;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostMapper postMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    public Page<Post> findAll(int page, int size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, size));

        // Regra de negócio
        if (!posts.isEmpty()) {
            return posts;
        }
        else {
            throw new ResourceNotFoundException(posts);
        }
    }

    public Page<Post> findAllByUserId(Long id, int page, int size) {
        User user = userService.findById(id);
        Page<Post> posts = postRepository.findByUser_Id(user.getId(), PageRequest.of(page, size));

        // Regra de negócio
        if (!posts.isEmpty()) {
            return posts;
        }
        else {
            throw new ResourceNotFoundException(posts);
        }
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Post createPost(Long userId, PostRequestDTO dto) {
        User user = userService.findById(userId);
        Post post = postMapper.toEntity(dto);
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Long userId, PostRequestDTO dto) {
        // Regra de negócio
        boolean exists = postRepository.existsByIdAndUser_Id(postId, userId);
        if (!exists) {
            throw new UnauthorizedActionException(userId);
        }

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

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));

        // Regra de negócio
        if (!post.getUser().getId().equals(userId)) {
            throw new UnauthorizedActionException(userId);
        }
        postRepository.delete(post);
    }
}