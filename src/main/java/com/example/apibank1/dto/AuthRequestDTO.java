package com.example.apibank1.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}