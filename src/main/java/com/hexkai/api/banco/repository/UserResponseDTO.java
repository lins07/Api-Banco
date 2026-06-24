package com.hexkai.api.banco.repository;

import java.util.UUID;

import com.hexkai.api.banco.domain.models.User;

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