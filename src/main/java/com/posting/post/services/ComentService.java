package com.posting.post.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.posting.post.entities.Coment;
import com.posting.post.repositories.ComentRepository;

@Service
public class ComentService {

    @Autowired
    ComentRepository comentRepository;

    public List<Coment> findAllByPostId(Long id) {
        return comentRepository.findByIdPostId(id);
    }
}
