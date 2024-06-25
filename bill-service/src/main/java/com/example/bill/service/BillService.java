package com.example.bill.service;

import com.example.bill.сlient.account.AccountServiceClient;
import com.example.bill.сlient.account.dto.AccountResponseDto;
import com.example.bill.сlient.account.dto.AddBookRequestDto;
import com.example.bill.сlient.account.dto.EditBillRequestDto;
import com.example.bill.сlient.book.BookForSaleResponseDto;
import com.example.bill.сlient.book.BookServiceClient;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.dto.BillResponseDtoToRabbit;
import com.example.bill.dto.BookResponseDtoToRabbit;
import com.example.bill.entity.Bill;
import com.example.bill.exception.AccountException;
import com.example.bill.exception.BillServiceException;
import com.example.bill.mapper.BillMapper;
import com.example.bill.repository.BillRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    //RabbitMQ
    private final RabbitTemplate rabbitTemplate;

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";

    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private static final String TOPIC_EXCHANGE_BOOK = "js.book.notify.exchange";

    private static final String ROUTING_KEY_BOOK = "js.key.book";
    public BillResponseDto createBill(UUID accountId, BigDecimal amount) {
        log.info("start find, {}, {}", accountId, amount);
        AccountResponseDto accountById = getAccountById(accountId);

        if (accountById.getBillId() != null) {
            throw new AccountException("The account already has a bill with id " + accountById.getBillId());
        }
        Bill bill = new Bill(UUID.randomUUID(), accountId, accountById.getEmail(), amount);
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
        BillResponseDto billResponseDto = billMapper.toResponse(bill);
        return sendDepositMessageToRabbit(deposit, billResponseDto);
    }

    private BillResponseDto sendDepositMessageToRabbit(BigDecimal deposit, BillResponseDto billResponseDto) {
        log.info("Sending message to email {}", billResponseDto.getEmail());
        BillResponseDtoToRabbit billResponseDtoToRabbit = new BillResponseDtoToRabbit(billResponseDto.getId(), billResponseDto.getEmail(), deposit);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                    objectMapper.writeValueAsString(billResponseDtoToRabbit));
        } catch (
                JsonProcessingException e) {
            throw new BillServiceException("Can`t send message to RabbitMq");
        }
        return billResponseDto;
    }

    public BillResponseDto buyBook(UUID billId, UUID bookId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new BillServiceException("Bill with id " + billId + " not found"));
        log.info("Bill has been found {}, start to buy book with id {}", bill, bookId);
        BookForSaleResponseDto book = bookServiceClient.getBookForSale(bookId);
        if (bill.getAmount().compareTo(BigDecimal.valueOf(book.getCost())) < 0) {
            throw new BillServiceException("There is not enough money in the bill to buy a book");
        }
        bill.setAmount(bill.getAmount().subtract(BigDecimal.valueOf(book.getCost())));
        billRepository.save(bill);
        log.info("Bill amount after buy book {}", bill.getAmount());

        AddBookRequestDto addBookRequestDto = new AddBookRequestDto(bill.getAccountId(), bookId);
        log.info("Start to add book to account {}", addBookRequestDto.getAccountId());
        accountServiceClient.addBookToAccount(addBookRequestDto);

        sendBookMessageToRabbit(bill.getEmail(), addBookRequestDto);

        return billMapper.toResponse(bill);
    }

    private void sendBookMessageToRabbit(String email, AddBookRequestDto addBookRequestDto) {
        log.info("Sending message to email {}", email);
        BookResponseDtoToRabbit bookResponseDtoToRabbit = new BookResponseDtoToRabbit(addBookRequestDto.getAccountId(), email, addBookRequestDto.getBookId());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_BOOK, ROUTING_KEY_BOOK,
                    objectMapper.writeValueAsString(bookResponseDtoToRabbit));
        } catch (
                JsonProcessingException e) {
            throw new BillServiceException("Can`t send message to RabbitMq");
        }
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
