package com.example.apibank1.service;

import com.example.apibank1.dto.AuthRequestDTO;
import com.example.apibank1.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO register(AuthRequestDTO request);
    AuthResponseDTO login(AuthRequestDTO request);
}