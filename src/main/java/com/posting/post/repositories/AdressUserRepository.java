package com.posting.post.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.posting.post.entities.AdressUser;

import java.util.Optional;

@Repository
public interface AdressUserRepository extends JpaRepository<AdressUser, Long>{

    Optional<AdressUser> findByUserId(Long id);

    Page<AdressUser> findAll(Pageable pageable);
}