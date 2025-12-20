package com.posting.post.resources;

import com.posting.post.dto.request.AdressUserRequestDTO;
import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.mapper.AdressUserMapper;
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
public class AdressUserResource {

    private final AdressUserService adressUserService;
    private final AdressUserMapper adressUserMapper;

    public AdressUserResource(AdressUserService adressUserService, AdressUserMapper adressUserMapper) {
        this.adressUserService = adressUserService;
        this.adressUserMapper = adressUserMapper;
    }

    @GetMapping
    public ResponseEntity<Page<AdressUserResponseDTO>> findAll(@RequestParam int page,
                                                               @RequestParam int size) {

        return ResponseEntity.ok().body(adressUserService.findAll(page, size).map(adressUserMapper::toAdressUser));

    }

    @GetMapping(value = "/me")
    public ResponseEntity<AdressUserResponseDTO> findByUserId() {
        return ResponseEntity.ok().body(adressUserMapper.toAdressUser(adressUserService.findAdressUserByUserId()));
    }

    @PostMapping
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
    public ResponseEntity<AdressUserResponseDTO> updateAdressUser(@RequestBody
                                                                  @Valid
                                                                  AdressUserRequestDTO body) {

        return ResponseEntity.ok().body(adressUserMapper.toAdressUser(adressUserService.updateAdressUser(body)));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAdressUser() {
        adressUserService.deleteAdressUser();
        return ResponseEntity.noContent().build();
    }
}