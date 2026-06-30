package com.hexkai.api.banco.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDTO(
        UUID targetAccountId, 
        BigDecimal amount     
) {}