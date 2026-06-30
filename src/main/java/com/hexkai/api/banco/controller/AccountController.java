package com.hexkai.api.banco.controller;

import com.hexkai.api.banco.controller.dto.AccountCreateDTO;
import com.hexkai.api.banco.controller.dto.AccountResponseDTO;
import com.hexkai.api.banco.controller.dto.TransactionRequestDTO;
import com.hexkai.api.banco.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

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

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponseDTO> deposit(
            @PathVariable UUID id, 
            @RequestBody TransactionRequestDTO dto) {
        
        AccountResponseDTO response = accountService.deposit(id, dto);
        return ResponseEntity.ok(response); 
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponseDTO> withdraw(
            @PathVariable UUID id, 
            @RequestBody TransactionRequestDTO dto) {
        
        AccountResponseDTO response = accountService.withdraw(id, dto);
        return ResponseEntity.ok(response); 
    }
}