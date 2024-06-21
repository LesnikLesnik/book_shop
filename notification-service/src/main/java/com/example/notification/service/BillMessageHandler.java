package com.example.notification.service;

import com.example.notification.dto.BillResponseDtoToRabbit;
import com.example.notification.dto.BookResponseDtoToRabbit;
import com.example.notification.config.RabbitMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillMessageHandler {

    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receiveDeposit(Message message) throws JsonProcessingException {
        log.info("Message to receive {} ", message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        BillResponseDtoToRabbit billResponseDtoToRabbit = objectMapper.readValue(jsonBody, BillResponseDtoToRabbit.class);
        log.info("BillResponseDto {}", billResponseDtoToRabbit);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(billResponseDtoToRabbit.getEmail());
        mailMessage.setFrom("kazulina.nast@gmail.com");

        mailMessage.setSubject("Deposit");
        mailMessage.setText("Make deposit sum: " + billResponseDtoToRabbit.getDeposit() +
                "\nBill id: " + billResponseDtoToRabbit.getId());

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception exception) {
            log.info(exception.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_BOOK)
    public void receiveBook(Message message) throws JsonProcessingException {
        log.info("Message to receive {} ", message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        BookResponseDtoToRabbit bookResponseDtoToRabbit = objectMapper.readValue(jsonBody, BookResponseDtoToRabbit.class);
        log.info("BookResponseDtoToRabbit {}", bookResponseDtoToRabbit);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(bookResponseDtoToRabbit.getEmail());
        mailMessage.setFrom("kazulina.nast@gmail.com");

        mailMessage.setSubject("Add book");
        mailMessage.setText("The book with id " + bookResponseDtoToRabbit.getBookId() + " has been added to the account " + bookResponseDtoToRabbit.getAccountId());

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception exception) {
            log.info(exception.getMessage());
        }
    }
}