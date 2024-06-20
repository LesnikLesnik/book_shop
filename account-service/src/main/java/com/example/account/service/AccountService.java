package com.example.account.service;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.dto.AddBillRequestDto;
import com.example.account.dto.AddBookRequestDto;
import com.example.account.entity.Account;
import com.example.account.exception.AccountNotFoundException;
import com.example.account.mapper.AccountMapper;
import com.example.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;


    public AccountResponseDto getAccountById(UUID id) {
        Optional<Account> accountById = accountRepository.findById(id);
        log.info("Find account with id {} completed", id);
        return accountById.map(accountMapper::toResponseDto)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id: " + id + " не найден"));
    }

    public Page<AccountResponseDto> getAllAccounts(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);
        log.info("Find all accounts successful");
        return accounts.map(accountMapper::toResponseDto);
    }

    public AccountResponseDto updateAccount(UUID id, AccountRequestDto accountRequestDto) {
        Account accountToUpdate = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id: " + id + " не найден"));
        log.info("Account before update {}", accountToUpdate);
        accountMapper.updateAccountFromDto(accountRequestDto, accountToUpdate);
        Account updatedAccount = accountRepository.save(accountToUpdate);
        updatedAccount.setId(id);
        updatedAccount.setCreationDate(accountToUpdate.getCreationDate());
        log.info("Account after update {}", updatedAccount);
        return accountMapper.toResponseDto(updatedAccount);
    }


    public void deleteAccount(UUID id) {
        log.info("Delete account with id {}", id);
        accountRepository.deleteById(id);
    }

    public AccountResponseDto addBillToAccount(AddBillRequestDto addBillRequestDto) {
        Account account = accountRepository.findById(addBillRequestDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id: " + addBillRequestDto.getAccountId() + " не найден"));
        log.info("Account before edit bill {}", account);
        account.setBillId(addBillRequestDto.getBillId());
        accountRepository.save(account);
        log.info("Account with edited bill {}", account);
        return accountMapper.toResponseDto(account);
    }

    public AccountResponseDto addBookToAccount(AddBookRequestDto addBookRequestDto) {
        Account account = accountRepository.findById(addBookRequestDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id: " + addBookRequestDto.getAccountId() + " не найден"));
        log.info("Account before adding the book {}", account);

        List<UUID> books = account.getBooks() != null ? account.getBooks() : new ArrayList<>();
        books.add(addBookRequestDto.getBookId());
        account.setBooks(books);

        Account savedAccount = accountRepository.save(account);
        log.info("Account after adding the book {}", account);
        return accountMapper.toResponseDto(savedAccount);
    }

}
