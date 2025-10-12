package com.posting.post.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.posting.post.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByName(String name);
}

