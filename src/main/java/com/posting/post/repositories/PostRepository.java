package com.posting.post.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.posting.post.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
