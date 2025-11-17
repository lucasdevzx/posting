package com.posting.post.resources;

import com.posting.post.dto.request.AdressUserRequestDTO;
import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.mapper.AdressUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.AdressUser;
import com.posting.post.services.AdressUserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/adress_user")
public class AdressUserResource {

    @Autowired
    AdressUserService adressUserService;

    @Autowired
    AdressUserMapper adressUserMapper;

    @GetMapping
    public ResponseEntity<Page<AdressUserResponseDTO>> findAll(@RequestParam int page,
                                                               @RequestParam int size) {

        return ResponseEntity.ok().body(adressUserService.findAll(page, size).map(adressUserMapper::toAdressUser));

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdressUserResponseDTO> findByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().body(adressUserMapper.toAdressUser(adressUserService.findByUserId(id)));
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<AdressUserResponseDTO> createAdressUser(@PathVariable Long userId, @RequestBody AdressUserRequestDTO body) {
        var adressUser = adressUserService.createAdressUser(userId, body);

        ServletUriComponentsBuilder.fromCurrentRequest();
        URI uri = UriComponentsBuilder.fromPath("/{id}")
                .buildAndExpand(adressUser.getId()).toUri();

        return ResponseEntity.created(uri).body(adressUserMapper.toAdressUser(adressUser));
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<AdressUserResponseDTO> updateAdressUser(@PathVariable Long userId,
                                                                  @RequestBody AdressUserRequestDTO body) {

        return ResponseEntity.ok().body(adressUserMapper.toAdressUser(adressUserService.updateAdressUser(userId, body)));
    }
}