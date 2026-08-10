package com.example.apibank1.repository;

import com.example.apibank1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Comprueba si un email ya está registrado para evitar duplicados
    boolean existsByEmail(String email);

    // Busca un usuario por su email (útil para la futura autenticación)
    Optional<User> findByEmail(String email);
}