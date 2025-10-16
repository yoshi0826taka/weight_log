package com.example.weight_log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.weight_log.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
