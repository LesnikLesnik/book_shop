package com.example.bill.service;

import com.example.bill.client.account.AccountServiceClient;
import com.example.bill.client.account.dto.AccountResponseDto;
import com.example.bill.client.account.dto.AddBookRequestDto;
import com.example.bill.client.account.dto.EditBillRequestDto;
import com.example.bill.client.book.BookForSaleResponseDto;
import com.example.bill.client.book.BookServiceClient;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.entity.Bill;
import com.example.bill.exception.AccountException;
import com.example.bill.exception.BillServiceException;
import com.example.bill.mapper.BillMapper;
import com.example.bill.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    private final AccountServiceClient accountServiceClient;

    private final BookServiceClient bookServiceClient;

    public BillResponseDto createBill(UUID accountId, BigDecimal amount) {
        log.info("start find, {}, {}", accountId, amount);
        AccountResponseDto accountById = getAccountById(accountId);

        if (accountById.getBillId() != null) {
            throw new AccountException("The account already has a bill with id " + accountById.getBillId());
        }
        Bill bill = new Bill(UUID.randomUUID(), accountId, amount);
        billRepository.save(bill);
        log.info("Bill with id {} has been created", bill.getId());

        editBillOnAccount(accountById, bill);
        log.info("The Bill creation was successful {}", bill);
        return billMapper.toResponse(bill);
    }

    private AccountResponseDto getAccountById(UUID accountId) {
        AccountResponseDto accountById = accountServiceClient.getAccount(accountId);
        log.info("Start to add bill to account with id {}", accountById);
        return accountById;
    }

    private void editBillOnAccount(AccountResponseDto accountById, Bill bill) {
        EditBillRequestDto editBillRequestDto = new EditBillRequestDto();
        editBillRequestDto.setBillId(bill.getId());
        editBillRequestDto.setAccountId(accountById.getId());
        accountServiceClient.editBillOnAccount(editBillRequestDto);
    }


    public BillResponseDto getBillById(UUID id) {
        return billRepository.findById(id)
                .map(billMapper::toResponse)
                .orElseThrow(() -> new BillServiceException("Bill with id " + id + " not found"));
    }

    public BillResponseDto deposit(UUID id, BigDecimal deposit) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillServiceException("Bill with id " + id + " not found"));
        log.info("Start to update bill {}", bill);
        bill.setAmount(bill.getAmount().add(deposit));
        billRepository.save(bill);
        log.info("Bill after update {}", bill);
        return billMapper.toResponse(bill);
    }

    public BillResponseDto buyBook(UUID billId, UUID bookId) {
        Bill bill = billRepository.findById(billId)
                        .orElseThrow(() -> new BillServiceException("Bill with id " + billId + " not found"));
        log.info("Bill has been found {}, start to buy book with id {}", bill, bookId);
        BookForSaleResponseDto book = bookServiceClient.getBookForSale(bookId);
        bill.setAmount(bill.getAmount().subtract(BigDecimal.valueOf(book.getCost())));
        log.info("Bill amount after buy book {}", bill.getAmount());

        AddBookRequestDto addBookRequestDto = new AddBookRequestDto(bill.getAccountId(), bookId);
        log.info("Start to add book to account {}", addBookRequestDto);
        accountServiceClient.addBookToAccount(addBookRequestDto);

        return billMapper.toResponse(bill);
    }

    public void deleteBill(UUID id) {
        log.info("Start to delete bill with ud {}", id);
        BillResponseDto billResponseDto = getBillById(id);
        EditBillRequestDto editBillRequestDto = new EditBillRequestDto();
        editBillRequestDto.setBillId(null);
        editBillRequestDto.setAccountId(billResponseDto.getAccountId());
        accountServiceClient.editBillOnAccount(editBillRequestDto);
        billRepository.deleteById(id);
        log.info("Delete bill with id {} successful", id);
    }

}
