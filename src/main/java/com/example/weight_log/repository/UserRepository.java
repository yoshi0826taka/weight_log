package com.example.weight_log.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.weight_log.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
