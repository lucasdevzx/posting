package com.posting.post.resources;

import java.net.URI;

import com.posting.post.dto.request.PostRequestDTO;
import com.posting.post.dto.response.PostResponseDTO;
import com.posting.post.mapper.PostMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Postagens")
public class PostResource {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostResource(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    @Operation(summary = "Busca todas as postagens", description = "Retorna postagens em paginação")
    public ResponseEntity<Page<PostResponseDTO>> findAll(@RequestParam int page, @RequestParam int size) {
        Page<Post> posts = postService.findAll(page, size);
        return ResponseEntity.ok().body(posts.map(postMapper::toPost));
    }

    @GetMapping(value = "/me")
    @Operation(summary = "Busca todas as postagens do usuário atual", description = "Retorna postagens do usuário atual em paginação")
    public ResponseEntity<Page<PostResponseDTO>> findAllByUserId(@RequestParam int page,
                                                                 @RequestParam int size) {

        Page<Post> posts = postService.findPostsByUserId(page, size);
        return ResponseEntity.ok().body(posts.map(postMapper::toPost));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca uma postagem por Id", description = "Retorna uma única postagem. Apenas para Admins")
    public ResponseEntity<PostResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(postMapper.toPost(postService.findById(id)));
    }

    @PostMapping
    @Operation(summary = "Cria uma postagem para o usuário atual", description = "Retorna uma postagem")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody
                                                      @Valid PostRequestDTO dto) {

        Post post = postService.createPost(dto);
        URI uri = ServletUriComponentsBuilder.fromPath("/{userId}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(uri).body(postMapper.toPost(post));
    }

    @PutMapping(value = "/{postId}")
    @Operation(summary = "Atualiza uma postagem por Id", description = "Retorna uma postagem. Apenas para Criadores")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long postId,
                                                      @RequestBody
                                                          @Valid
                                                          PostRequestDTO dto) {

        return ResponseEntity.ok().body(postMapper.toPost(postService.updatePost(postId, dto)));
    }

    @DeleteMapping(value = "/{postId}")
    @Operation(summary = "Deleta uma postagem por Id", description = "Retorna vazio. Apenas para Criadores e Admins")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
