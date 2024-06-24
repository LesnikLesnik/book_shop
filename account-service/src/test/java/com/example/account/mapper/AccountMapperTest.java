package com.example.account.mapper;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

    private AccountMapper accountMapper;

    private UUID accountId;
    private Date creationDate;

    @BeforeEach
    void setUp() {
        accountMapper = Mappers.getMapper(AccountMapper.class);
        accountId = UUID.randomUUID();
        creationDate = new Date();
    }

    @Test
    void toAccount_givenAccountRequestDto() {
        //Given
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setLogin("testLogin");
        accountRequestDto.setEmail("testEmail@example.com");
        accountRequestDto.setPassword("testPassword");

        //When
        Account account = accountMapper.toAccount(accountRequestDto);

        //Then
        assertNotNull(account);
        assertEquals("testLogin", account.getLogin());
        assertEquals("testEmail@example.com", account.getEmail());
        assertEquals("testPassword", account.getPassword());
        assertNotNull(account.getCreationDate()); // Creation date устанавливается в toAccount методе
    }

    @Test
    void toResponseDto_givenAccount() {
        //Given
        Account account = new Account(accountId, "testLogin", "testEmail@example.com", "testPassword", creationDate, null, "USER", null);

        //When
        AccountResponseDto accountResponseDto = accountMapper.toResponseDto(account);

        //Then
        assertNotNull(accountResponseDto);
        assertEquals(accountId, accountResponseDto.getId());
        assertEquals("testLogin", accountResponseDto.getLogin());
        assertEquals("testEmail@example.com", accountResponseDto.getEmail());
        assertEquals(creationDate, accountResponseDto.getCreationDate());
    }

    @Test
    void updateAccountFromDto_shouldUpdateEntity() {
        //Given
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setLogin("updatedLogin");
        accountRequestDto.setEmail("updatedEmail@example.com");
        accountRequestDto.setPassword("updatedPassword");

        //When
        Account account = new Account(accountId, "oldLogin", "oldEmail@example.com", "oldPassword", creationDate, null, "USER", null);

        accountMapper.updateAccountFromDto(accountRequestDto, account);

        //Then
        assertNotNull(account);
        assertEquals("updatedLogin", account.getLogin());
        assertEquals("updatedEmail@example.com", account.getEmail());
        assertEquals("updatedPassword", account.getPassword());
        assertEquals(accountId, account.getId()); // ID не изменяется
        assertEquals(creationDate, account.getCreationDate()); // Дата создания не изменяется
    }
}
