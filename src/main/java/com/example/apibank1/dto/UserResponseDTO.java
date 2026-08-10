package com.example.apibank1.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
    // OJO: No incluimos la contraseña aquí por seguridad
}