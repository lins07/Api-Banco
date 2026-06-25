package com.hexkai.api.banco.service;

import com.hexkai.api.banco.controller.dto.UserCreateDTO;
import com.hexkai.api.banco.controller.dto.UserResponseDTO;
import com.hexkai.api.banco.domain.model.User;
import com.hexkai.api.banco.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDTO findByCpf(String cpf) {
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com o CPF informado não foi encontrado."));
        return new UserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        
        if (userRepository.findByCpf(dto.cpf()).isPresent()) {
            throw new IllegalArgumentException("O CPF informado já está cadastrado.");
        }

   
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("O e-mail informado já está cadastrado.");
        }

       
        User newUser = new User();
        newUser.setFullName(dto.fullName());
        newUser.setCpf(dto.cpf());
        newUser.setEmail(dto.email());
        newUser.setPassword(dto.password()); 

        User savedUser = userRepository.save(newUser);
        return new UserResponseDTO(savedUser);
    }
}