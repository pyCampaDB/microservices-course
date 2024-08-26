package com.msvc.user.user_service.repositories;

import com.msvc.user.user_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
