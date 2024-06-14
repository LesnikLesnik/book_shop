package com.example.bill.service;

import com.example.bill.client.AccountServiceClient;
import com.example.bill.client.dto.AccountResponseDto;
import com.example.bill.client.dto.AddBillRequestDto;
import com.example.bill.dto.BillRequestDto;
import com.example.bill.entity.Bill;
import com.example.bill.exception.AccountException;
import com.example.bill.mapper.BillMapper;
import com.example.bill.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    private final AccountServiceClient accountServiceClient;
    public UUID createBill(BillRequestDto billRequestDto) {
        AccountResponseDto accountById = getAccountById(billRequestDto);

        if (accountById.getBillId() != null) {
            throw new AccountException("The account already has a bill with id " + accountById.getBillId());
        }
        Bill bill = billMapper.toBill(billRequestDto);
        bill.setId(UUID.randomUUID());
        billRepository.save(bill);
        log.info("Bill with id {} has been created", bill.getId());

        addBillToAccount(accountById, bill);
        log.info("The Bill creation was successful {}", bill);
        return bill.getId();
    }

    private AccountResponseDto getAccountById(BillRequestDto billRequestDto) {
        UUID accountId = billRequestDto.getAccountId();
        AccountResponseDto accountById = accountServiceClient.getAccount(accountId);
        log.info("Start to add bill to account with id {}", accountById);
        return accountById;
    }

    private void addBillToAccount(AccountResponseDto accountById, Bill bill) {
        AddBillRequestDto addBillRequestDto = new AddBillRequestDto();
        addBillRequestDto.setBillId(bill.getId());
        addBillRequestDto.setAccountId(accountById.getId());
        accountServiceClient.addBillToAccount(addBillRequestDto);
    }
}
