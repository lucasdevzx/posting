package com.posting.post.resources;

import java.net.URI;
import java.util.Locale;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.dto.request.PostRequestDTO;
import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.mapper.PostMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.Post;
import com.posting.post.services.PostService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostResource(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping(value = "/me")
    public ResponseEntity<Page<PostResponseDTO>> findAllByUserId(@RequestParam int page,
                                                                 @RequestParam int size) {

        Page<Post> posts = postService.findAllByUserId(page, size);
        return ResponseEntity.ok().body(posts.map(postMapper::toPost));
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDTO>> findAll(@RequestParam int page, @RequestParam int size) {
        Page<Post> posts = postService.findAll(page, size);
        return ResponseEntity.ok().body(posts.map(postMapper::toPost));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(postMapper.toPost(postService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody
                                                      @Valid PostRequestDTO dto) {

        Post post = postService.createPost(dto);
        URI uri = ServletUriComponentsBuilder.fromPath("/{userId}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(uri).body(postMapper.toPost(post));
    }

    @PutMapping(value = "/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long postId,
                                                      @RequestBody
                                                          @Valid
                                                          PostRequestDTO dto) {

        return ResponseEntity.ok().body(postMapper.toPost(postService.updatePost(postId, dto)));
    }

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}