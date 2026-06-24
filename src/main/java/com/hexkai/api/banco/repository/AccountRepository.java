package com.hexkai.api.banco.repository;

import com.hexkai.api.banco.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
}