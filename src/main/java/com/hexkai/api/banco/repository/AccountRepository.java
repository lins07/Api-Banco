package com.hexkai.api.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexkai.api.banco.domain.models.Account;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
}