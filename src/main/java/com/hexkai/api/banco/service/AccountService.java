package com.hexkai.api.banco.service;

import com.hexkai.api.banco.controller.dto.AccountCreateDTO;
import com.hexkai.api.banco.controller.dto.AccountResponseDTO;
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

        // 3. Salva e retorna
        Account savedAccount = accountRepository.save(newAccount);
        return new AccountResponseDTO(savedAccount);
    }

    
    private String generateAccountNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }
}