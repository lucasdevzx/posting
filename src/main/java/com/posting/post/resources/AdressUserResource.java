package com.posting.post.resources;

import com.posting.post.dto.response.AdressUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.posting.post.entities.AdressUser;
import com.posting.post.services.AdressUserService;

@RestController
@RequestMapping(value = "/adress_user")
public class AdressUserResource {

    @Autowired
    AdressUserService adressUserService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdressUserResponseDTO> findByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().body(adressUserService.findByUserId(id));
    }
}
