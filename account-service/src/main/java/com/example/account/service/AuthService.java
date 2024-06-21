package com.example.account.service;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.dto.AuthResponseDto;
import com.example.account.dto.LoginRequestDto;
import com.example.account.entity.Account;
import com.example.account.exception.AccountNotFoundException;
import com.example.account.mapper.AccountMapper;
import com.example.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper; // Используем AccountMapper для преобразования сущности Account в AccountResponseDto
    private final JwtUtils jwtUtils;

    public AuthResponseDto register(AccountRequestDto requestDto) {
        // Проверка, существует ли пользователь с таким email
        if (accountRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalStateException("Пользователь с таким email уже существует");
        }

        // Шифрование пароля
        requestDto.setPassword(BCrypt.hashpw(requestDto.getPassword(), BCrypt.gensalt()));

        // Преобразование DTO в сущность
        Account account = accountMapper.toAccount(requestDto);
        account.setId(UUID.randomUUID());
        account.setRole("USER"); // По умолчанию роль пользователя "USER"

        // Сохранение аккаунта
        accountRepository.save(account);

        // Преобразование сохраненной сущности в DTO
        AccountResponseDto accountResponseDto = accountMapper.toResponseDto(account);

        // Генерация JWT токенов
        String accessToken = jwtUtils.generate(account.getId().toString(), account.getRole(), "ACCESS");
        String refreshToken = jwtUtils.generate(account.getId().toString(), account.getRole(), "REFRESH");

        log.info("Регистрация завершена для пользователя: {}", account.getEmail());

        return new AuthResponseDto(accessToken, refreshToken, accountResponseDto);
    }

    public AuthResponseDto login(LoginRequestDto requestDto) {
        // Поиск пользователя по email
        Account account = accountRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new AccountNotFoundException("Не найден пользователь с email " + requestDto.getEmail()));

        // Проверка пароля
        if (!BCrypt.checkpw(requestDto.getPassword(), account.getPassword())) {
            throw new IllegalStateException("Неверный пароль");
        }

        // Преобразование сущности в DTO
        AccountResponseDto accountResponseDto = accountMapper.toResponseDto(account);

        // Генерация JWT токенов
        String accessToken = jwtUtils.generate(account.getId().toString(), account.getRole(), "ACCESS");
        String refreshToken = jwtUtils.generate(account.getId().toString(), account.getRole(), "REFRESH");

        log.info("Аутентификация успешна для пользователя: {}", account.getEmail());

        return new AuthResponseDto(accessToken, refreshToken, accountResponseDto);
    }
}
