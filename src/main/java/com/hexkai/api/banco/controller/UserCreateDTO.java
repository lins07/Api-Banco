package com.hexkai.api.banco.controller;

public record UserCreateDTO(
        String fullName,
        String cpf,
        String email,
        String password
) {}