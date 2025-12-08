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

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<Page<PostResponseDTO>> findAllByUserId(@PathVariable Long userId,
                                                                 @RequestParam int page,
                                                                 @RequestParam int size) {

        Page<Post> posts = postService.findAllByUserId(userId, page, size);
        return ResponseEntity.ok().body(posts.map(postMapper::toPost));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(postMapper.toPost(postService.findById(id)));
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<PostResponseDTO> createPost(@PathVariable Long userId,
                                                      @RequestBody
                                                      @Valid PostRequestDTO dto) {

        Post post = postService.createPost(userId, dto);
        URI uri = ServletUriComponentsBuilder.fromPath("/{userId}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(uri).body(postMapper.toPost(post));
    }

    @PutMapping(value = "/{postId}/{userId}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long postId,
                                                      @PathVariable Long userId,
                                                      @RequestBody
                                                          @Valid
                                                          PostRequestDTO dto) {

        return ResponseEntity.ok().body(postMapper.toPost(postService.updatePost(postId, userId, dto)));
    }

    @DeleteMapping(value = "/{postId}/{userId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @PathVariable Long userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}