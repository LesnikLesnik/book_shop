package com.example.account.controller;

import com.example.account.controller.annotations.DefaultApiResponses;
import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.dto.AddBillRequestDto;
import com.example.account.dto.AddBookRequestDto;
import com.example.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@DefaultApiResponses
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Tag(description = "Account controller", name = "Контроллер аккаунтов")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/")
    @Operation(summary = "Создание аккаунта")
    public UUID createAccount(@RequestBody AccountRequestDto accountRequestDTO){
        return accountService.createAccount(accountRequestDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение аккаунта по id")
    public AccountResponseDto getAccount(@PathVariable UUID id){
        return accountService.getAccountById(id);
    }

    @GetMapping
    @Operation(summary = "Получение всех аккаунтов по 15 штук на одной странице")
    public Page<AccountResponseDto> getAllAccounts(@PageableDefault(size = 15) Pageable pageable){
        return accountService.getAllAccounts(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление аккаунта", description = "Не обновляются id и дата создания")
    public AccountResponseDto updateAccount(@PathVariable UUID id, @RequestBody AccountRequestDto accountRequestDto){
        return accountService.updateAccount(id, accountRequestDto);
    }

    @PutMapping("/bill")
    @Operation(summary = "Добавление счета в аккаунт",
            description = "Запрос от bill-service для добавления счета в соответствующее поле в аккаунте")
    public AccountResponseDto editBillOnAccount(@RequestBody AddBillRequestDto addBillRequestDto){
        return accountService.addBillToAccount(addBillRequestDto);
    }

    @PutMapping("/book")
    @Operation(summary = "Добавление книги в аккаунт",
            description = "Запрос от bill-service для добавления книги в соответствующее поле в аккаунте после ее оплаты")
    AccountResponseDto addBookToAccount(@RequestBody AddBookRequestDto addBookRequestDto) {
        return accountService.addBookToAccount(addBookRequestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление аккаунта")
    public void deleteAccount(@PathVariable UUID id){
        accountService.deleteAccount(id);
    }
}
