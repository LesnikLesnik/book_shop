package com.example.bill.client.account;

import com.example.bill.client.account.dto.AccountResponseDto;
import com.example.bill.client.account.dto.AddBookRequestDto;
import com.example.bill.client.account.dto.EditBillRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @GetMapping(value = "/api/accounts/{id}")
    AccountResponseDto getAccount(@PathVariable(value = "id") UUID id);

    @PutMapping("/api/accounts/bill")
    AccountResponseDto editBillOnAccount(@RequestBody EditBillRequestDto editBillRequestDto);

    @PutMapping("/api/account/book")
    AccountResponseDto addBookToAccount(@RequestBody AddBookRequestDto addBookRequestDto);
}

