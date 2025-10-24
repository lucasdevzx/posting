package com.posting.post.resources;

import java.net.URI;
import java.util.List;

import com.posting.post.dto.request.ComentRequestDTO;
import com.posting.post.dto.response.ComentResponseDTO;
import com.posting.post.entities.Post;
import com.posting.post.mapper.ComentMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.Coment;
import com.posting.post.services.ComentService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/coments")
public class ComentResource {

    @Autowired
    ComentService comentService;

    @Autowired
    ComentMapper comentMapper;

    @GetMapping(value = "/{postId}")
    public ResponseEntity<List<Coment>> findAllByPostId(@PathVariable Long postId) {
        List<Coment> list = comentService.findAllByPostId(postId);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/{userId}/{postId}")
    public ResponseEntity<ComentResponseDTO> createComent(@PathVariable Long userId,
                                                          @PathVariable Long postId,
                                                          @RequestBody
                                                              @Valid
                                                              ComentRequestDTO dto) {

        Coment coment = comentService.createComent(userId, postId, dto);
        URI uri = ServletUriComponentsBuilder.fromPath("/{postId}")
                .buildAndExpand(coment.getComent())
                .toUri();

        return ResponseEntity.created(uri).body(comentMapper.toComent(coment));
    }
}