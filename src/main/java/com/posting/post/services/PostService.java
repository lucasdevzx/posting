package com.posting.post.services;

import java.util.List;

import com.posting.post.entities.User;
import com.posting.post.mapper.PostMapper;
import com.posting.post.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByUserId(Long id) {
        User user = userService.findById(id);
        List<Post> posts = postRepository.findByUser_Id(user.getId());
        if (!posts.isEmpty()) {
            return posts;
        }
        else {
            throw new ResourceNotFoundException(posts);
        }
    }
}