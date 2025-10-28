package com.posting.post.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.posting.post.entities.Coment;

@Repository
public interface ComentRepository extends JpaRepository<Coment, Long>{

    Page<Coment> findByPostId(Long id, Pageable pageable);

    boolean existsByUserIdAndPostId(Long userId, Long postId);
}
