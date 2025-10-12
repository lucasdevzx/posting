package com.posting.post.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.posting.post.dto.profile.ProfileDTO;
import com.posting.post.services.ProfileService;

@RestController
@RequestMapping(value = "/profile")
public class ProfileResource {

    @Autowired
    ProfileService profileService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProfileDTO> ProfileById(@PathVariable Long id) {
        return ResponseEntity.ok().body(profileService.fullProfile(id));
    }
}
