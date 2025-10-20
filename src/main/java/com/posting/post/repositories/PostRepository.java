package com.posting.post.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.posting.post.entities.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser_Id(Long id);

    boolean existsByIdAndUser_Id(Long postId, Long userId);
}
