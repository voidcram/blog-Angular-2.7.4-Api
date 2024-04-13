package com.marcos.blog.repositories;

import com.marcos.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
}
