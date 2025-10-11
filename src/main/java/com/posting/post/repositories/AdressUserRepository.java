package com.posting.post.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.posting.post.entities.AdressUser;

@Repository
public interface AdressUserRepository extends JpaRepository<AdressUser, Long>{
}
