package com.posting.post.services;

import java.util.List;

import com.posting.post.dto.request.ComentRequestDTO;
import com.posting.post.entities.Post;
import com.posting.post.entities.User;
import com.posting.post.mapper.ComentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.posting.post.entities.Coment;
import com.posting.post.repositories.ComentRepository;

@Service
public class ComentService {

    @Autowired
    ComentRepository comentRepository;

    @Autowired
    ComentMapper comentMapper;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    public Page<Coment> findAllByPostId(Long id, int page, int size) {
        return comentRepository.findByIdPostId(id, PageRequest.of(page, size));
    }

    public Coment createComent(Long userId, Long postId, ComentRequestDTO dto) {
        User user = userService.findById(userId);
        Post post = postService.findById(postId);
        Coment coment = comentMapper.toEntity(dto);
        coment.setUser(user);
        coment.setPost(post);
        return comentRepository.save(coment);
    }
}