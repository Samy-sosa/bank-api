package com.example.apibank1.repository;

import com.example.apibank1.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Busca una cuenta por su número único
    Optional<Account> findByAccountNumber(String accountNumber);

    // Obtiene todas las cuentas asociadas a un usuario específico
    List<Account> findByUserId(Long userId);

    // Comprueba si ya existe un número de cuenta
    boolean existsByAccountNumber(String accountNumber);
}