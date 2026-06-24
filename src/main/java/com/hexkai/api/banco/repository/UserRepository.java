package com.hexkai.api.banco.repository;

import com.hexkai.api.banco.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    Optional<User> findByCpf(String cpf);
    Optional<User> findByEmail(String email);
}