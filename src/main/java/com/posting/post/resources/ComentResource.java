package com.posting.post.resources;

import java.util.List;

import com.posting.post.dto.request.ComentRequestDTO;
import com.posting.post.dto.response.ComentResponseDTO;
import com.posting.post.mapper.ComentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.Coment;
import com.posting.post.services.ComentService;

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
                                                          @RequestBody ComentRequestDTO coment) {

        return ResponseEntity.ok().body(comentMapper.toComent(comentService.createComent(userId, postId, coment)));
    }
}