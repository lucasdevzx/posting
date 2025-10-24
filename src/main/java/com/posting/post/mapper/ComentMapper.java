package com.posting.post.mapper;

import com.posting.post.dto.request.ComentRequestDTO;
import com.posting.post.dto.response.ComentResponseDTO;
import com.posting.post.entities.Coment;
import org.springframework.stereotype.Component;

@Component
public class ComentMapper {

    public ComentResponseDTO toComent(Coment coment) {
        return new ComentResponseDTO(
                coment.getUser().getId(),
                coment.getPost().getId(),
                coment.getUser().getName(),
                coment.getComent(),
                coment.getDate());
    }

    public Coment toEntity(ComentRequestDTO dto) {
        Coment coment = new Coment();
        coment.setComent(dto.coment());
        return coment;
    }
}
