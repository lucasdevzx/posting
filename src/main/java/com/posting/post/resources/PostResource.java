package com.posting.post.resources;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import com.posting.post.dto.request.PostRequestDTO;
import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.Post;
import com.posting.post.services.PostService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/post")
public class PostResource {

    @Autowired
    PostService postService;

    @Autowired
    PostMapper postMapper;

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        List<Post> list = postService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id,
                                      @RequestParam(value = "view", defaultValue = "detail") String view) {
        List<Post> posts = postService.findByUserId(id);

        switch (view.toLowerCase(Locale.ROOT)) {
            case "profile":
                return ResponseEntity.ok().body(postMapper.toPosts(posts));

            case "detail":
            default:
                return ResponseEntity.ok().body(posts);
        }
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<PostResponseDTO> createPost(@PathVariable Long userId, @RequestBody PostRequestDTO obj) {
        Post post = postService.createPost(userId, obj);
        URI uri = ServletUriComponentsBuilder.fromPath("/{userId}").buildAndExpand(post.getUser().getId()).toUri();
        return ResponseEntity.created(uri).body(postMapper.toPost(post));
    }

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}
