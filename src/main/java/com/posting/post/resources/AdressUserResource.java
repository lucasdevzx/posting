package com.posting.post.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.posting.post.entities.AdressUser;
import com.posting.post.services.AdressUserService;

@RestController
@RequestMapping(value = "/adress_user")
public class AdressUserResource {

    @Autowired
    AdressUserService adressUserService;

    @GetMapping
    public ResponseEntity<List<AdressUser>> findAll() {
        List<AdressUser> list = adressUserService.findAll();
        return ResponseEntity.ok().body(list);
    }

}
