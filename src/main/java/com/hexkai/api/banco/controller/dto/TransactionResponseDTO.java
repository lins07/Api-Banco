package com.hexkai.api.banco.controller.dto;

import com.hexkai.api.banco.domain.model.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID id,
        String type,
        BigDecimal amount,
        LocalDateTime createdAt
) {
    public TransactionResponseDTO(Transaction t) {
        this(t.getId(), t.getType().name(), t.getAmount(), t.getCreatedAt());
    }
}