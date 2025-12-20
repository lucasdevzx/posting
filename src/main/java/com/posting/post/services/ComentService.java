package com.posting.post.services;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.dto.request.ComentRequestDTO;
import com.posting.post.entities.Post;
import com.posting.post.entities.User;
import com.posting.post.mapper.ComentMapper;
import com.posting.post.services.exceptions.ResourceNotFoundException;
import com.posting.post.services.exceptions.UnauthorizedActionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.posting.post.entities.Coment;
import com.posting.post.repositories.ComentRepository;

@Service
public class ComentService {

    private final ComentRepository comentRepository;
    private final ComentMapper comentMapper;

    private final PostService postService;
    private final AuthenticatedUserService authenticatedUserService;

    public ComentService(ComentRepository comentRepository,
                         ComentMapper comentMapper,
                         PostService postService,
                         AuthenticatedUserService authenticatedUserService) {

        this.comentRepository = comentRepository;
        this.comentMapper = comentMapper;
        this.postService = postService;
        this.authenticatedUserService = authenticatedUserService;
    }

    public Page<Coment> findComentsByPostId(Long id, int page, int size) {
        return comentRepository.findByPostId(id, PageRequest.of(page, size));
    }

    @PreAuthorize("isAuthenticated()")
    public Coment createComent(Long postId, ComentRequestDTO dto) {
        User user = authenticatedUserService.getCurrentUser();
        Post post = postService.findById(postId);
        Coment coment = comentMapper.toEntity(dto);
        coment.setUser(user);
        coment.setPost(post);
        return comentRepository.save(coment);
    }

    @PreAuthorize("isAuthenticated()")
    public Coment updateComent(Long id, ComentRequestDTO dto) {
        Long userId = authenticatedUserService.getCurrentUserId();

        // Regra de negócio
        boolean exists = comentRepository.existsByIdAndUser_Id(id, userId);
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

    @PreAuthorize("isAuthenticated()")
    public void deleteComent(Long comentId) {
        Long userId = authenticatedUserService.getCurrentUserId();
        Coment coment = comentRepository.findById(comentId)
                .orElseThrow(() -> new ResourceNotFoundException("Coment not found with id " + comentId));

        // Regra de negócio
        boolean isOwner = coment.getUser().getId().equals(userId);
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedActionException(userId);
        }
        comentRepository.delete(coment);
    }
}