package com.posting.post.resources;

import java.util.List;
import java.util.Locale;

import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.mapper.PostMapper;
import com.posting.post.services.ComentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.Post;
import com.posting.post.services.PostService;

@RestController
@RequestMapping(value = "/post")
public class PostResource {

    @Autowired
    PostService postService;

    @Autowired
    ComentService comentService;

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
                return ResponseEntity.ok().body(postMapper.toPost(posts));

            case "detail":
            default:
                return ResponseEntity.ok().body(posts);
        }
    }
}
