package com.example.apibank1.controller;

import com.example.apibank1.dto.AccountCreateDTO;
import com.example.apibank1.dto.AccountResponseDTO;
import com.example.apibank1.dto.TransferRequestDTO;
import com.example.apibank1.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // 👈 Indica a Swagger que este controller usa JWT
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountCreateDTO accountCreateDTO) {
        AccountResponseDTO createdAccount = accountService.createAccount(accountCreateDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        accountService.transferMoney(transferRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDTO> getAccountByNumber(@PathVariable String accountNumber) {
        AccountResponseDTO account = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountResponseDTO>> getAccountsByUserId(@PathVariable Long userId) {
        List<AccountResponseDTO> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }
}