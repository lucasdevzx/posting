package com.posting.post.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.posting.post.entities.Coment;
import com.posting.post.pk.ComentPK;

@Repository
public interface ComentRepository extends JpaRepository<Coment, ComentPK>{

    List<Coment> findByIdPostId(Long id);
}
