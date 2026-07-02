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

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UserResponseDTO> findByCpf(@PathVariable String cpf) {
        UserResponseDTO response = userService.findByCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO dto) {
        UserResponseDTO response = userService.createUser(dto);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
                
        return ResponseEntity.created(uri).body(response);
    }
}