package com.hexkai.api.banco.controller.dto;

import java.util.UUID;

import com.hexkai.api.banco.domain.model.User;

public record UserResponseDTO(
        UUID id,
        String fullName,
        String cpf,
        String email
) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getFullName(), user.getCpf(), user.getEmail());
    }
}