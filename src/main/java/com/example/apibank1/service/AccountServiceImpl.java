package com.example.apibank1.service;

import com.example.apibank1.dto.AccountCreateDTO;
import com.example.apibank1.dto.AccountResponseDTO;
import com.example.apibank1.dto.TransferRequestDTO;
import com.example.apibank1.model.Account;
import com.example.apibank1.model.AccountStatus;
import com.example.apibank1.model.Transaction;
import com.example.apibank1.model.TransactionType;
import com.example.apibank1.model.User;
import com.example.apibank1.repository.AccountRepository;
import com.example.apibank1.repository.TransactionRepository;
import com.example.apibank1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public AccountResponseDTO createAccount(AccountCreateDTO dto) {
        // Validar que el usuario exista
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUserId()));

        // Generar un número de cuenta único (ej: ACC-9A8B7C6D)
        String accountNumber = "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(dto.getInitialBalance())
                .type(dto.getType())
                .status(AccountStatus.ACTIVE)
                .user(user)
                .build();

        Account savedAccount = accountRepository.save(account);

        return mapToResponseDTO(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con número: " + accountNumber));
        return mapToResponseDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAccountsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + userId);
        }
        return accountRepository.findByUserId(userId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional // Garantiza la atomicidad de la transferencia (ACID)
    public void transferMoney(TransferRequestDTO dto) {
        // 1. Validar que no se transfiera a la misma cuenta
        if (dto.getSourceAccountNumber().equals(dto.getTargetAccountNumber())) {
            throw new RuntimeException("No puedes realizar una transferencia a la misma cuenta");
        }

        // 2. Buscar cuenta origen
        Account sourceAccount = accountRepository.findByAccountNumber(dto.getSourceAccountNumber())
                .orElseThrow(() -> new RuntimeException("Cuenta de origen no encontrada"));

        // 3. Buscar cuenta destino
        Account targetAccount = accountRepository.findByAccountNumber(dto.getTargetAccountNumber())
                .orElseThrow(() -> new RuntimeException("Cuenta de destino no encontrada"));

        // 4. Validar saldo suficiente
        if (sourceAccount.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new RuntimeException("Saldo insuficiente en la cuenta de origen");
        }

        // 5. Restar de la cuenta origen y sumar en la de destino
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(dto.getAmount()));
        targetAccount.setBalance(targetAccount.getBalance().add(dto.getAmount()));

        // 6. Guardar cambios de cuentas
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        // 7. Registrar el comprobante/auditoría de la transacción
        Transaction transaction = Transaction.builder()
                .amount(dto.getAmount())
                .type(TransactionType.TRANSFER)
                .sourceAccount(sourceAccount)
                .targetAccount(targetAccount)
                .build();

        transactionRepository.save(transaction);
    }

    private AccountResponseDTO mapToResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .type(account.getType())
                .status(account.getStatus())
                .userId(account.getUser().getId())
                .createdAt(account.getCreatedAt())
                .build();
    }
}