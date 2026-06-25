package com.hexkai.api.banco.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.hexkai.api.banco.domain.model.Account;

public record AccountResponseDTO(
        UUID id,
        String accountNumber,
        String agency,
        BigDecimal balance,
        String accountType,
        UUID userId
) {
    public AccountResponseDTO(Account account) {
        this(
            account.getId(), 
            account.getAccountNumber(), 
            account.getAgency(), 
            account.getBalance(), 
            account.getAccountType().name(), 
            account.getUser().getId()
        );
    }
}