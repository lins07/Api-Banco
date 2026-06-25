package com.hexkai.api.banco.controller;

import com.hexkai.api.banco.controller.dto.AccountCreateDTO;
import com.hexkai.api.banco.controller.dto.AccountResponseDTO;
import com.hexkai.api.banco.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountCreateDTO dto) {
        AccountResponseDTO response = accountService.createAccount(dto);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
                
        return ResponseEntity.created(uri).body(response);
    }
}