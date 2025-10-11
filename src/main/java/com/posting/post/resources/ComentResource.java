package com.posting.post.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.posting.post.entities.Coment;
import com.posting.post.services.ComentService;

@RestController
@RequestMapping(value = "/coment")
public class ComentResource {

    @Autowired
    ComentService comentService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<Coment>> findAllByPostId(@PathVariable Long id) {
        List<Coment> list = comentService.findAllByPostId(id);
        return ResponseEntity.ok().body(list);
    }
}
