package com.example.account.controller;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.dto.AddBillRequestDto;
import com.example.account.dto.AddBookRequestDto;
import com.example.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/")
    public UUID createAccount(@RequestBody AccountRequestDto accountRequestDTO){
        return accountService.createAccount(accountRequestDTO);
    }

    @GetMapping("/{id}")
    public AccountResponseDto getAccount(@PathVariable UUID id){
        return accountService.getAccountById(id);
    }

    @GetMapping
    public Page<AccountResponseDto> getAllAccounts(@PageableDefault(size = 15) Pageable pageable){
        return accountService.getAllAccounts(pageable);
    }

    @PutMapping("/{id}")
    public AccountResponseDto updateAccount(@PathVariable UUID id, @RequestBody AccountRequestDto accountRequestDto){
        return accountService.updateAccount(id, accountRequestDto);
    }

    @PutMapping("/bill")
    public AccountResponseDto editBillOnAccount(@RequestBody AddBillRequestDto addBillRequestDto){
        return accountService.addBillToAccount(addBillRequestDto);
    }

    @PutMapping("/api/account/book")
    AccountResponseDto addBookToAccount(@RequestBody AddBookRequestDto addBookRequestDto) {
        return accountService.addBookToAccount(addBookRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable UUID id){
        accountService.deleteAccount(id);
    }
}
