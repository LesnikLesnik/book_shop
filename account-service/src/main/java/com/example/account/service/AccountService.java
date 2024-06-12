package com.example.account.service;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.entity.Account;
import com.example.account.exception.AccountNotFoundException;
import com.example.account.mapper.AccountMapper;
import com.example.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;


    public UUID createAccount(AccountRequestDto accountRequestDTO) {
        Account account = accountMapper.toAccount(accountRequestDTO);
        account.setId(UUID.randomUUID());
        log.info("Create account with id {} completed: {}", account.getId(), account);
        return accountRepository.save(account).getId();
    }

    public AccountResponseDto getAccountById(UUID id) {
        Optional<Account> accountById = accountRepository.findById(id);
        log.info("Find account with id {} completed", id);
        return accountById.map(accountMapper::toResponseDto)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id: " + id + " не найден"));
    }

    public Page<AccountResponseDto> getAllAccounts(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);
        return accounts.map(accountMapper::toResponseDto);
    }

    public AccountResponseDto updateAccount(UUID id, AccountRequestDto accountRequestDto) {
        Account accountToUpdate = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id: " + id + " не найден"));
        log.info("Find account with id {} completed", id);
        accountToUpdate.setId(id);
        log.info("Account before update {}", accountToUpdate);
        accountMapper.updateAccountFromDto(accountRequestDto, accountToUpdate);
        Account updatedAccount = accountRepository.save(accountToUpdate);
        log.info("Account after update {}", updatedAccount);
        return accountMapper.toResponseDto(updatedAccount);
    }


    public void deleteAccount(UUID id) {
        log.info("Delete account with id {}", id);
        accountRepository.deleteById(id);
    }
}
