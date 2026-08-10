package com.example.apibank1.service;

import com.example.apibank1.dto.AccountCreateDTO;
import com.example.apibank1.dto.AccountResponseDTO;


import java.util.List;

public interface AccountService {
    AccountResponseDTO createAccount(AccountCreateDTO accountCreateDTO);
    AccountResponseDTO getAccountByNumber(String accountNumber);
    List<AccountResponseDTO> getAccountsByUserId(Long userId);

}