package com.posting.post.resources;

import java.net.URI;

import com.posting.post.dto.request.ComentRequestDTO;
import com.posting.post.dto.response.ComentResponseDTO;
import com.posting.post.mapper.ComentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.Coment;
import com.posting.post.services.ComentService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/coments")
@Tag(name = "Comentários")
public class ComentResource {

    private final ComentService comentService;
    private final ComentMapper comentMapper;

    public ComentResource(ComentService comentService, ComentMapper comentMapper) {
        this.comentService = comentService;
        this.comentMapper = comentMapper;
    }

    @GetMapping(value = "/{postId}")
    @Operation(summary = "Busca todos comentários por Id de Postagem", description = "Retorna comentários em paginação")
    public ResponseEntity<Page<ComentResponseDTO>> findAllByPostId(@PathVariable Long postId,
                                                        @RequestParam int page,
                                                        @RequestParam int size) {

        Page<Coment> coments = comentService.findComentsByPostId(postId, page, size);
        return ResponseEntity.ok().body(coments.map(comentMapper::toComent));
    }

    @PostMapping(value = "/{postId}")
    @Operation(summary = "Cria um comentário por Id de Postagem", description = "Retorna um comentário. Utiliza o usuário atual")
    public ResponseEntity<ComentResponseDTO> createComent(@PathVariable Long postId,
                                                          @RequestBody
                                                              @Valid
                                                              ComentRequestDTO dto) {

        Coment coment = comentService.createComent(postId, dto);
        URI uri = ServletUriComponentsBuilder.fromPath("/{postId}")
                .buildAndExpand(coment.getComent())
                .toUri();

        return ResponseEntity.created(uri).body(comentMapper.toComent(coment));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualiza um comentário por Id", description = "Retorna um comentário. Apenas para Criadores")
    public ResponseEntity<ComentResponseDTO> updateComent(@PathVariable Long id,
                                                          @RequestBody
                                                              @Valid
                                                              ComentRequestDTO dto) {

        return ResponseEntity.ok().body(comentMapper.toComent(comentService.updateComent(id, dto)));

    }

    @DeleteMapping(value = "/{comentId}")
    @Operation(summary = "Deleta um comentário por Id", description = "Retorno vazio. Apenas para Criadores e Admins")
    public ResponseEntity<Void> deleteComent(@PathVariable Long comentId) {
        comentService.deleteComent(comentId);
        return ResponseEntity.noContent().build();
    }
}