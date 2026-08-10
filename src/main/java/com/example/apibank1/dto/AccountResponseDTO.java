package com.example.apibank1.dto;

import com.example.apibank1.model.AccountStatus;
import com.example.apibank1.model.AccountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDTO {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private AccountType type;
    private AccountStatus status;
    private Long userId;
    private LocalDateTime createdAt;
}