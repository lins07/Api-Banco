package com.hexkai.api.banco.service;

import com.hexkai.api.banco.controller.dto.AccountCreateDTO;
import com.hexkai.api.banco.controller.dto.AccountResponseDTO;
import com.hexkai.api.banco.controller.dto.TransactionRequestDTO;
import com.hexkai.api.banco.domain.enums.AccountType;
import com.hexkai.api.banco.domain.model.Account;
import com.hexkai.api.banco.domain.model.User;
import com.hexkai.api.banco.repository.AccountRepository;
import com.hexkai.api.banco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    public AccountResponseDTO createAccount(AccountCreateDTO dto) {
        
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        
        Account newAccount = new Account();
        newAccount.setUser(user);
        newAccount.setAccountType(AccountType.valueOf(dto.accountType().toUpperCase()));
        newAccount.setAgency("0001"); 
        newAccount.setAccountNumber(generateAccountNumber()); 
        newAccount.setBalance(BigDecimal.ZERO); 

        Account savedAccount = accountRepository.save(newAccount);
        return new AccountResponseDTO(savedAccount);
    }

    @Transactional
    public AccountResponseDTO deposit(UUID accountId, TransactionRequestDTO dto) {
        // Validação básica: O valor precisa ser maior que zero
        if (dto.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }

        // Busca a conta
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        // Adiciona o valor ao saldo atual
        account.setBalance(account.getBalance().add(dto.amount()));

        // Salva e devolve o DTO atualizado
        Account savedAccount = accountRepository.save(account);
        return new AccountResponseDTO(savedAccount);
    }

    @Transactional
    public AccountResponseDTO withdraw(UUID accountId, TransactionRequestDTO dto) {
        if (dto.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        if (account.getBalance().compareTo(dto.amount()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
        }

        account.setBalance(account.getBalance().subtract(dto.amount()));

        Account savedAccount = accountRepository.save(account);
        return new AccountResponseDTO(savedAccount);
    }


    private String generateAccountNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }
}