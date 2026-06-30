package com.hexkai.api.banco.controller.dto;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        BigDecimal amount 
) {}