package com.hexkai.api.banco.controller.dto;

public record UserCreateDTO(
        String fullName,
        String cpf,
        String email,
        String password
) {}