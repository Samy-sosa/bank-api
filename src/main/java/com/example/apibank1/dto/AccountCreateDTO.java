package com.example.apibank1.dto;

import com.example.apibank1.model.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreateDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private AccountType type;

    @NotNull(message = "El saldo inicial no puede ser nulo")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private BigDecimal initialBalance;
}