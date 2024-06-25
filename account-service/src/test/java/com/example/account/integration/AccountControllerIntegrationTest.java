package com.example.account.integration;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.dto.AuthResponseDto;
import com.example.account.entity.Account;
import com.example.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class AccountControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();  // Очистка тестовой базы данных перед каждым тестом

        account = new Account();
        account.setId(UUID.randomUUID());
        account.setLogin("testLogin");
        account.setEmail("testEmail@example.com");
        account.setPassword("testPassword");
        account.setCreationDate(new Date());
        account.setRole("USER");

        accountRepository.save(account);
    }

    @Test
    void getAccountById_shouldReturnAccount() {
        ResponseEntity<AccountResponseDto> response = restTemplate.getForEntity(
                createURLWithPort("/api/accounts/" + account.getId()),
                AccountResponseDto.class
        );

        assertNotNull(response.getBody());
        assertEquals(account.getId(), response.getBody().getId());
        assertEquals(account.getLogin(), response.getBody().getLogin());
        assertEquals(account.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void registerAccount_shouldReturnAccountResponseDto() {
        AccountRequestDto request = new AccountRequestDto("newLogin", "password", "newEmail@example.com");
        ResponseEntity<AuthResponseDto> response = restTemplate.postForEntity("/api/auth/register", request, AuthResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        AuthResponseDto authResponse = response.getBody();
        assertNotNull(authResponse);
        assertNotNull(authResponse.getAccount());
        assertEquals("newLogin", authResponse.getAccount().getLogin());
    }


    @Test
    void updateAccount_shouldReturnUpdatedAccount() {
        AccountRequestDto updateRequest = new AccountRequestDto();
        updateRequest.setLogin("updatedLogin");
        updateRequest.setEmail("updatedEmail@example.com");
        updateRequest.setPassword("updatedPassword");

        HttpEntity<AccountRequestDto> requestEntity = new HttpEntity<>(updateRequest);

        ResponseEntity<AccountResponseDto> response = restTemplate.exchange(
                createURLWithPort("/api/accounts/" + account.getId()),
                HttpMethod.PUT,
                requestEntity,
                AccountResponseDto.class
        );

        assertNotNull(response.getBody());
        assertEquals("updatedLogin", response.getBody().getLogin());
        assertEquals("updatedEmail@example.com", response.getBody().getEmail());

        // Проверка, что аккаунт действительно обновлен в базе данных
        Account updatedAccount = accountRepository.findById(account.getId()).orElse(null);
        assertNotNull(updatedAccount);
        assertEquals("updatedLogin", updatedAccount.getLogin());
        assertEquals("updatedEmail@example.com", updatedAccount.getEmail());
    }


    @Test
    void deleteAccount_shouldRemoveAccount() {
        restTemplate.delete(createURLWithPort("/api/accounts/" + account.getId()));

        // Проверка, что аккаунт действительно удален из базы данных
        Account deletedAccount = accountRepository.findById(account.getId()).orElse(null);
        assertNull(deletedAccount);
    }
}
