package com.hexkai.api.banco.service;

import com.hexkai.api.banco.controller.dto.AccountCreateDTO;
import com.hexkai.api.banco.controller.dto.AccountResponseDTO;
import com.hexkai.api.banco.controller.dto.TransactionRequestDTO;
import com.hexkai.api.banco.controller.dto.TransactionResponseDTO;
import com.hexkai.api.banco.controller.dto.TransferRequestDTO;
import com.hexkai.api.banco.domain.enums.AccountType;
import com.hexkai.api.banco.domain.enums.TransactionType;
import com.hexkai.api.banco.domain.model.Account;
import com.hexkai.api.banco.domain.model.Transaction;
import com.hexkai.api.banco.domain.model.User;
import com.hexkai.api.banco.repository.AccountRepository;
import com.hexkai.api.banco.repository.TransactionRepository;
import com.hexkai.api.banco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

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
        
        if (dto.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }

        
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

       
        account.setBalance(account.getBalance().add(dto.amount()));

        registerTransaction(account, TransactionType.DEPOSIT, dto.amount());
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

        registerTransaction(account, TransactionType.WITHDRAWAL, dto.amount());

        Account savedAccount = accountRepository.save(account);
        return new AccountResponseDTO(savedAccount);
    }


    private String generateAccountNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }

    @Transactional
    public AccountResponseDTO transfer(UUID sourceAccountId, TransferRequestDTO dto) {
        
        if (dto.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

       
        if (sourceAccountId.equals(dto.targetAccountId())) {
            throw new IllegalArgumentException("Não é possível transferir para a própria conta.");
        }

       
        Account sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada."));

       
        Account targetAccount = accountRepository.findById(dto.targetAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada."));

       
        if (sourceAccount.getBalance().compareTo(dto.amount()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência.");
        }

      
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(dto.amount()));
        targetAccount.setBalance(targetAccount.getBalance().add(dto.amount()));

        registerTransaction(sourceAccount, TransactionType.TRANSFER_OUT, dto.amount());
        registerTransaction(targetAccount, TransactionType.TRANSFER_IN, dto.amount());

       
        accountRepository.save(targetAccount);
        Account savedSourceAccount = accountRepository.save(sourceAccount);

        return new AccountResponseDTO(savedSourceAccount);
    }

    
    private void registerTransaction(Account account, TransactionType type, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType(type);
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getStatement(UUID accountId) {
        
        if (!accountRepository.existsById(accountId)) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }

        return transactionRepository.findByAccountIdOrderByCreatedAtDesc(accountId)
                .stream()
                .map(TransactionResponseDTO::new)
                .toList();
    }
}