package com.project.book_shop.security.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendRegistrationMessage(String to, UUID token) {
        String subject = "Welcome to Book Shop!";
        String confirmationUrl = "http://localhost:8080/users/register/" + token.toString();
        String text = "Короче, Меченый, я тебя спас и в благородство играть не буду: Выполнишь для меня пару заданий - и мы в расчете. Для начала активируй аккаунт: \n" + confirmationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }



}
