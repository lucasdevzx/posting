package com.posting.post.resources;

import java.net.URI;
import java.util.Locale;

import com.posting.post.dto.request.PostRequestDTO;
import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.mapper.PostMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.Post;
import com.posting.post.services.PostService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping(value = "/post")
public class PostResource {

    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostResponseDTO>> findAll(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok().body(postService.findAll(page, size));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Page<PostResponseDTO>> findAllByUserId(
            @PathVariable Long id,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok().body(postService.findAllByUserId(id, page, size));
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<PostResponseDTO> createPost(
            @PathVariable Long userId,
            @RequestBody @Valid PostRequestDTO dto) {

        PostResponseDTO post = postService.createPost(userId, dto);
        URI uri = ServletUriComponentsBuilder
                .fromPath("/{userId}")
                .buildAndExpand(dto)
                .toUri();

        return ResponseEntity.created(uri).body(postService.createPost(userId, dto));
    }

    @PutMapping(value = "/{postId}/{userId}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @RequestBody @Valid PostRequestDTO dto) {

        return ResponseEntity.ok().body(postService.updatePost(postId, userId, dto));
    }

    @DeleteMapping(value = "/{postId}/{userId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @PathVariable Long userId) {

        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}