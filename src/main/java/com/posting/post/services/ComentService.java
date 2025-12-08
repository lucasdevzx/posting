package com.posting.post.services;

import com.posting.post.dto.request.ComentRequestDTO;
import com.posting.post.entities.Post;
import com.posting.post.entities.User;
import com.posting.post.mapper.ComentMapper;
import com.posting.post.repositories.PostRepository;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.exceptions.ResourceNotFoundException;
import com.posting.post.services.exceptions.UnauthorizedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.posting.post.entities.Coment;
import com.posting.post.repositories.ComentRepository;

@Service
public class ComentService {

    private final ComentRepository comentRepository;

    private final ComentMapper comentMapper;

    private final UserService userService;

    private final PostService postService;

    public ComentService(ComentRepository comentRepository,
                         ComentMapper comentMapper,
                         UserService userService,
                         PostService postService) {

        this.comentRepository = comentRepository;
        this.comentMapper = comentMapper;
        this.userService = userService;
        this.postService = postService;
    }

    public Page<Coment> findAllByPostId(Long id, int page, int size) {
        return comentRepository.findByPostId(id, PageRequest.of(page, size));
    }

    public Coment createComent(Long userId, Long postId, ComentRequestDTO dto) {
        User user = userService.findById(userId);
        Post post = postService.findById(postId);
        Coment coment = comentMapper.toEntity(dto);
        coment.setUser(user);
        coment.setPost(post);
        return comentRepository.save(coment);
    }

    public Coment updateComent(Long id, Long userId, Long postId, ComentRequestDTO dto) {
        // Regra de negócio
        boolean exists = comentRepository.existsByUserIdAndPostId(userId, postId);
        if (!exists) {
            throw new UnauthorizedActionException(userId);
        }

        Coment entity = comentRepository.getReferenceById(id);
        Coment obj = comentMapper.toEntity(dto);
        updateData(entity, obj);
        return comentRepository.save(entity);
    }

    // Auxílio Update
    private void updateData(Coment entity, Coment obj) {
        entity.setComent(obj.getComent());
    }

    public void deleteComent(Long comentId, Long userId) {
        Coment coment = comentRepository.findById(comentId).orElseThrow(() -> new ResourceNotFoundException(comentId));

        // Regra de negócio
        if (!coment.getUser().getId().equals(userId)) {
            throw new UnauthorizedActionException(userId);
        }
        comentRepository.deleteById(comentId);
    }
}