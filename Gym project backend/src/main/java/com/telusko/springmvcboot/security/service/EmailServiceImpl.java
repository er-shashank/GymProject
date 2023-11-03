package com.telusko.springmvcboot.security.service;

import com.telusko.springmvcboot.utilities.OTPgenerator;
import com.telusko.springmvcboot.utilities.constants.SimpleMessageStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl{

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, SimpleMessageStrings subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply-workout@gmail.com");
        message.setTo(to);
        message.setSubject(subject.getText());
        message.setText(text);
        emailSender.send(message);

    }
}