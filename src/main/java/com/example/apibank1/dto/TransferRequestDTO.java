package com.example.apibank1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequestDTO {

    @NotBlank(message = "El número de cuenta de origen es obligatorio")
    private String sourceAccountNumber;

    @NotBlank(message = "El número de cuenta de destino es obligatorio")
    private String targetAccountNumber;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto a transferir debe ser mayor a 0")
    private BigDecimal amount;
}