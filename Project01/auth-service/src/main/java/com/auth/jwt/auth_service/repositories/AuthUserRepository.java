package com.auth.jwt.auth_service.repositories;

import com.auth.jwt.auth_service.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    //Optional returns true if exists or false if not exists
    Optional<AuthUser> findByUsername(String username);

}
