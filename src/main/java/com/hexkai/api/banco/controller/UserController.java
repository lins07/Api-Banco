package com.hexkai.api.banco.controller;

import com.hexkai.api.banco.controller.dto.UserCreateDTO;
import com.hexkai.api.banco.controller.dto.UserResponseDTO;
import com.hexkai.api.banco.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO dto) {
        // Envia o DTO para o Service fazer as validações e salvar no banco
        UserResponseDTO response = userService.createUser(dto);
        
        // Padrão de mercado (REST Nível 2): Retorna o status 201 (Created) 
        // e coloca no cabeçalho (Header) o link exato de onde o usuário recém-criado pode ser acessado.
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
                
        return ResponseEntity.created(uri).body(response);
    }
}