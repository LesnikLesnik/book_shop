package com.project.book_shop.security.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendRegistrationMessage(String to) {
        String subject = "Оставь надежду всяк сюда входящий";
        String text = "Короче, Меченый, я тебя спас и в благородство играть не буду: Выполнишь для меня пару заданий - и мы в расчете";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }



}
