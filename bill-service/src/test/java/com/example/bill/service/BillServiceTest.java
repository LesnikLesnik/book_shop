package com.example.bill.service;

import com.example.bill.dto.BillResponseDto;
import com.example.bill.entity.Bill;
import com.example.bill.exception.BillServiceException;
import com.example.bill.mapper.BillMapper;
import com.example.bill.repository.BillRepository;
import com.example.bill.сlient.account.AccountServiceClient;
import com.example.bill.сlient.account.dto.AccountResponseDto;
import com.example.bill.сlient.book.BookForSaleResponseDto;
import com.example.bill.сlient.book.BookServiceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private BillMapper billMapper;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private BookServiceClient bookServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private BillService billService;

    private UUID accountId;
    private UUID billId;
    private UUID bookId;
    private BigDecimal amount;
    private Bill bill;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountId = UUID.randomUUID();
        billId = UUID.randomUUID();
        bookId = UUID.randomUUID();
        amount = BigDecimal.valueOf(100.00);
        bill = new Bill(billId, accountId, "test@example.com", amount);
    }

    @Test
    void createBill_shouldCreateAndReturnBill() {
        //Given
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(accountId);
        accountResponseDto.setEmail("test@example.com");

        //When
        when(accountServiceClient.getAccount(accountId)).thenReturn(accountResponseDto);
        when(billRepository.save(any(Bill.class))).thenReturn(bill);
        when(billMapper.toResponse(any(Bill.class))).thenReturn(new BillResponseDto());

        BillResponseDto result = billService.createBill(accountId, amount);

        //Then
        assertNotNull(result);
        verify(billRepository).save(any(Bill.class));
    }

    @Test
    void getBillById_shouldReturnBill() {
        //When
        when(billRepository.findById(billId)).thenReturn(Optional.of(bill));
        when(billMapper.toResponse(bill)).thenReturn(new BillResponseDto());

        BillResponseDto result = billService.getBillById(billId);

        //Then
        assertNotNull(result);
        verify(billRepository).findById(billId);
    }

    @Test
    void deposit_shouldUpdateBillAmount() {
        //Given
        BigDecimal depositAmount = BigDecimal.valueOf(50.00);

        //When
        when(billRepository.findById(billId)).thenReturn(Optional.of(bill));
        when(billRepository.save(bill)).thenReturn(bill);
        when(billMapper.toResponse(bill)).thenReturn(new BillResponseDto());

        BillResponseDto result = billService.deposit(billId, depositAmount);

        //Then
        assertNotNull(result);
        assertEquals(amount.add(depositAmount), bill.getAmount());
        verify(billRepository).save(bill);
    }

    @Test
    void buyBook_shouldUpdateBillAmount() {
        //Given
        int bookCost = 50;
        BookForSaleResponseDto bookForSaleResponseDto = new BookForSaleResponseDto(bookId, bookCost);

        //When
        when(billRepository.findById(billId)).thenReturn(Optional.of(bill));
        when(bookServiceClient.getBookForSale(bookId)).thenReturn(bookForSaleResponseDto);
        when(billRepository.save(bill)).thenReturn(bill);
        when(billMapper.toResponse(bill)).thenReturn(new BillResponseDto());

        BillResponseDto result = billService.buyBook(billId, bookId);

        //Then
        assertNotNull(result);
        assertEquals(amount.subtract(BigDecimal.valueOf(bookCost)), bill.getAmount());
        verify(billRepository).save(bill);
    }

}
