package com.example.account.service;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.dto.AddBillRequestDto;
import com.example.account.dto.AddBookRequestDto;
import com.example.account.entity.Account;
import com.example.account.exception.AccountNotFoundException;
import com.example.account.mapper.AccountMapper;
import com.example.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private AccountRequestDto accountRequestDto;
    private AccountResponseDto accountResponseDto;
    private UUID accountId;

    @BeforeEach
    void setUp() {
        accountId = UUID.randomUUID();
        account = new Account(accountId, "login", "email@example.com", "password", new Date(), null, "USER", null);
        accountRequestDto = new AccountRequestDto();
        accountRequestDto.setLogin("newLogin");
        accountRequestDto.setEmail("newEmail@example.com");
        accountRequestDto.setPassword("newPassword");
        accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(accountId);
        accountResponseDto.setLogin("newLogin");
        accountResponseDto.setEmail("newEmail@example.com");
        accountResponseDto.setCreationDate(new Date());
    }

    @Test
    void getAccountById_existingId_returnsAccountResponseDto() {
        //Given
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toResponseDto(account)).thenReturn(accountResponseDto);

        //When
        AccountResponseDto result = accountService.getAccountById(accountId);

        //Then
        assertEquals(accountResponseDto, result);
        verify(accountRepository).findById(accountId);
        verify(accountMapper).toResponseDto(account);
    }

    @Test
    void getAccountById_nonExistingId_throwsAccountNotFoundException() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(accountId));

        verify(accountRepository).findById(accountId);
    }

    @Test
    void getAllAccounts_returnsPagedAccountResponseDto() {
        //Given
        Pageable pageable = PageRequest.of(0, 15);
        Page<Account> accountPage = new PageImpl<>(Arrays.asList(account));
        Page<AccountResponseDto> accountResponsePage = new PageImpl<>(Arrays.asList(accountResponseDto));

        //When
        when(accountRepository.findAll(pageable)).thenReturn(accountPage);
        when(accountMapper.toResponseDto(account)).thenReturn(accountResponseDto);

        Page<AccountResponseDto> result = accountService.getAllAccounts(pageable);

        //Then
        assertEquals(accountResponsePage.getTotalElements(), result.getTotalElements());
        assertEquals(accountResponsePage.getContent().get(0).getId(), result.getContent().get(0).getId());
        verify(accountRepository).findAll(pageable);
    }

    @Test
    void updateAccount_existingId_updatesAndReturnsAccountResponseDto() {
        //When
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toResponseDto(account)).thenReturn(accountResponseDto);

        AccountResponseDto result = accountService.updateAccount(accountId, accountRequestDto);

        //Then
        assertEquals(accountResponseDto, result);
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        verify(accountMapper).updateAccountFromDto(accountRequestDto, account);
    }

    @Test
    void updateAccount_nonExistingId_throwsAccountNotFoundException() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.updateAccount(accountId, accountRequestDto));

        verify(accountRepository).findById(accountId);
    }

    @Test
    void addBillToAccount_existingAccount_addsBillAndReturnsAccountResponseDto() {
        //Given
        UUID billId = UUID.randomUUID();
        AddBillRequestDto addBillRequestDto = new AddBillRequestDto();
        addBillRequestDto.setAccountId(accountId);
        addBillRequestDto.setBillId(billId);

        //When
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toResponseDto(account)).thenReturn(accountResponseDto);

        AccountResponseDto result = accountService.addBillToAccount(addBillRequestDto);

        //Then
        assertEquals(accountResponseDto, result);
        assertEquals(billId, account.getBillId());
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
    }

    @Test
    void addBillToAccount_nonExistingAccount_throwsAccountNotFoundException() {
        //Given
        UUID billId = UUID.randomUUID();
        AddBillRequestDto addBillRequestDto = new AddBillRequestDto();
        addBillRequestDto.setAccountId(accountId);
        addBillRequestDto.setBillId(billId);

        //When
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        //Then
        assertThrows(AccountNotFoundException.class, () -> accountService.addBillToAccount(addBillRequestDto));

        verify(accountRepository).findById(accountId);
    }

    @Test
    void addBookToAccount_existingAccount_addsBookAndReturnsAccountResponseDto() {
        //Given
        UUID bookId = UUID.randomUUID();
        AddBookRequestDto addBookRequestDto = new AddBookRequestDto(accountId, bookId);

        //When
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toResponseDto(account)).thenReturn(accountResponseDto);

        AccountResponseDto result = accountService.addBookToAccount(addBookRequestDto);

        //Then
        assertEquals(accountResponseDto, result);
        assertTrue(account.getBooks().contains(bookId));
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
    }

    @Test
    void addBookToAccount_nonExistingAccount_throwsAccountNotFoundException() {
        //Given
        UUID bookId = UUID.randomUUID();
        AddBookRequestDto addBookRequestDto = new AddBookRequestDto(accountId, bookId);

        //When
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.addBookToAccount(addBookRequestDto));

        //Then
        verify(accountRepository).findById(accountId);
    }

}
