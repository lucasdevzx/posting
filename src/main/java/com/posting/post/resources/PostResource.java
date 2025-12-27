package com.posting.post.resources;

import java.net.URI;

import com.posting.post.dto.request.PostRequestDTO;
import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.mapper.PostMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.Post;
import com.posting.post.services.PostService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostResource(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDTO>> findAll(@RequestParam int page, @RequestParam int size) {
        Page<Post> posts = postService.findAll(page, size);
        return ResponseEntity.ok().body(posts.map(postMapper::toPost));
    }

    @GetMapping(value = "/me")
    public ResponseEntity<Page<PostResponseDTO>> findAllByUserId(@RequestParam int page,
                                                                 @RequestParam int size) {

        Page<Post> posts = postService.findPostsByUserId(page, size);
        return ResponseEntity.ok().body(posts.map(postMapper::toPost));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(postMapper.toPost(postService.findById(id)));
    }

    @PostMapping(value = "/{postId}/{categoryId}")
    public ResponseEntity<PostResponseDTO> addCategoryInPost(@PathVariable Long postId, @PathVariable Long categoryId) {
        Post post = postService.addCategoryInPost(postId, categoryId);
        URI uri = ServletUriComponentsBuilder.fromPath("/{postId}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(uri).body(postMapper.toPost(post));
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