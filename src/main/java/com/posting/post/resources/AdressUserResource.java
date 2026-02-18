package com.posting.post.resources;

import com.posting.post.dto.request.AdressUserRequestDTO;
import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.mapper.AdressUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.services.AdressUserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/adress_users")
@Tag(name = "Endereços")
public class AdressUserResource {

    private final AdressUserService adressUserService;
    private final AdressUserMapper adressUserMapper;

    public AdressUserResource(AdressUserService adressUserService, AdressUserMapper adressUserMapper) {
        this.adressUserService = adressUserService;
        this.adressUserMapper = adressUserMapper;
    }

    @GetMapping
    @Operation(summary = "Busca todos endereços", description = "Retorna endereços em paginação. Apenas para Admins")
    public ResponseEntity<Page<AdressUserResponseDTO>> findAll(@RequestParam int page,
                                                               @RequestParam int size) {

        return ResponseEntity.ok().body(adressUserService.findAll(page, size).map(adressUserMapper::toAdressUser));

    }

    @GetMapping(value = "/me")
    @Operation(summary = "Busca endereço do usuário atual", description = "Retorna um único endereço do usuário atual")
    public ResponseEntity<AdressUserResponseDTO> findByUserId() {
        return ResponseEntity.ok().body(adressUserMapper.toAdressUser(adressUserService.findAdressUserByUserId()));
    }

    @PostMapping
    @Operation(summary = "Cria um novo endereço", description = "Retorna um único endereço. Utiliza o usuário atual")
    public ResponseEntity<AdressUserResponseDTO> createAdressUser(@RequestBody
                                                                  @Valid
                                                                  AdressUserRequestDTO body) {

        var adressUser = adressUserService.createAdressUser(body);
        ServletUriComponentsBuilder.fromCurrentRequest();
        URI uri = UriComponentsBuilder.fromPath("/{id}")
                .buildAndExpand(adressUser.getId()).toUri();

        return ResponseEntity.created(uri).body(adressUserMapper.toAdressUser(adressUser));
    }

    @PutMapping
    @Operation(summary = "Atualiza o endereço do usuário atual", description = "Retorna um único endereço. Apenas para criadores")
    public ResponseEntity<AdressUserResponseDTO> updateAdressUser(@RequestBody
                                                                  @Valid
                                                                  AdressUserRequestDTO body) {

        return ResponseEntity.ok().body(adressUserMapper.toAdressUser(adressUserService.updateAdressUser(body)));
    }

    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Deleta um endereço por Id do usuário", description = "Retorna vazio. Apenas para Criadores e Admins")
    public ResponseEntity<Void> deleteByUserId(@PathVariable Long userId) {
        adressUserService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "Deleta um endereço do usuário atual", description = "Retorna vazio. Apenas para Criadores e Admins")
    public ResponseEntity<Void> deleteAdressUser() {
        adressUserService.deleteAdressUser();
        return ResponseEntity.noContent().build();
    }
}