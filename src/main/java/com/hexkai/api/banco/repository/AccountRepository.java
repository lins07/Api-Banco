package com.hexkai.api.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexkai.api.banco.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
}