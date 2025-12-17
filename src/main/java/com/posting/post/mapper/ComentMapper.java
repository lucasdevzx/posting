package com.posting.post.mapper;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.config.UserDetailsImpl;
import com.posting.post.dto.request.ComentRequestDTO;
import com.posting.post.dto.response.AuthorResponseDTO;
import com.posting.post.dto.response.ComentResponseDTO;
import com.posting.post.dto.response.PermissionsResponseDTO;
import com.posting.post.entities.Coment;
import com.posting.post.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ComentMapper {

    private final AuthenticatedUserService authenticatedUserService;

    public ComentMapper(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
}

    public ComentResponseDTO toComent(Coment coment) {
        UserDetailsImpl currentUser = authenticatedUserService.getCurrentUserDetails();
        return new ComentResponseDTO(
                coment.getId(),
                coment.getComent(),
                new AuthorResponseDTO(
                        coment.getUser().getId(),
                        coment.getUser().getName()
                ),
                coment.getPost().getId(),
                coment.getDate(),

                new PermissionsResponseDTO(
                        checkEditPermission(coment, currentUser),
                        checkDeletePermission(coment, currentUser),
                        checkOwnership(coment, currentUser)
                ));
    }

    public Coment toEntity(ComentRequestDTO dto) {
        Coment coment = new Coment();
        coment.setComent(dto.coment());
        return coment;
    }

    public boolean checkEditPermission(Coment coment, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        return coment.getUser().getId().equals(currentUser.getUser().getId());
    }

    public boolean checkDeletePermission(Coment coment, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
        boolean isOwner = coment.getUser().getId().equals(currentUser.getUser().getId());
        return isOwner || isAdmin;
    }

    public boolean checkOwnership(Coment coment, UserDetailsImpl currentUser) {
        if (currentUser == null) return false;
        return coment.getUser().getId().equals(currentUser.getUser().getId());
    }
}
