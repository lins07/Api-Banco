package com.hexkai.api.banco.controller.dto;

import java.util.UUID;

public record AccountCreateDTO(
        UUID userId,
        String accountType // Ex: "CURRENT" (Corrente) ou "SAVINGS" (Poupança)
) {}