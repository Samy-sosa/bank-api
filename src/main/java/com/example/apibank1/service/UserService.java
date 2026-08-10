package com.example.apibank1.service;

import com.example.apibank1.dto.UserCreateDTO;
import com.example.apibank1.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserCreateDTO userCreateDTO);
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
}