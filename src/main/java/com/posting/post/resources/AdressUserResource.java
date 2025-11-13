package com.posting.post.resources;

import com.posting.post.dto.response.AdressUserResponseDTO;
import com.posting.post.mapper.AdressUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.AdressUser;
import com.posting.post.services.AdressUserService;

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
}