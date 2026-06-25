package com.hexkai.api.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexkai.api.banco.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    Optional<User> findByCpf(String cpf);
    Optional<User> findByEmail(String email);
}