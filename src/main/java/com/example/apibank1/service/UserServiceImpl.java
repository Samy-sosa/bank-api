package com.example.apibank1.service;

import com.example.apibank1.dto.UserCreateDTO;
import com.example.apibank1.dto.UserResponseDTO;
import com.example.apibank1.model.User;
import com.example.apibank1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        // Validación de negocio: Evitar correos duplicados
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email " + dto.getEmail() + " ya está registrado");
        }

        // Mapeo DTO -> Entidad
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword()) // Más adelante integraremos encriptación BCrypt aquí
                .build();

        User savedUser = userRepository.save(user);

        // Mapeo Entidad -> ResponseDTO
        return mapToResponseDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapToResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Método helper para convertir Entidad a DTO de Respuesta
    private UserResponseDTO mapToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}