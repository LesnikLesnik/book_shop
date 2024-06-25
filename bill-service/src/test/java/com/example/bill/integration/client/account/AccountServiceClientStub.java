package com.example.bill.integration.client.account;

import com.example.bill.сlient.account.AccountServiceClient;
import com.example.bill.сlient.account.dto.AccountResponseDto;
import com.example.bill.сlient.account.dto.AddBookRequestDto;
import com.example.bill.сlient.account.dto.EditBillRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.Date;
import java.util.List;

@Component
public class AccountServiceClientStub implements AccountServiceClient {

    @Override
    public AccountResponseDto getAccount(UUID id) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(id);
        accountResponseDto.setName("Test User");
        accountResponseDto.setEmail("testuser@example.com");
        accountResponseDto.setPassword("password");
        accountResponseDto.setCreationDate(new Date());
        accountResponseDto.setBillId(null);
        accountResponseDto.setBooks(List.of());
        return accountResponseDto;
    }

    @Override
    public AccountResponseDto editBillOnAccount(EditBillRequestDto editBillRequestDto) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(editBillRequestDto.getAccountId());
        accountResponseDto.setBillId(editBillRequestDto.getBillId());
        return accountResponseDto;
    }

    @Override
    public AccountResponseDto addBookToAccount(AddBookRequestDto addBookRequestDto) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(addBookRequestDto.getAccountId());
        accountResponseDto.setBooks(List.of(addBookRequestDto.getBookId()));
        return accountResponseDto;
    }
}
