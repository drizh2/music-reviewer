package edu.dadry.mailsender.service;

import edu.dadry.mailsender.messaging.EmailReceivedMessage;
import edu.dadry.mailsender.models.MessageData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    @Value("${mail.admin.email}")
    private String adminEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;

    public void sendBrokerMessage(EmailReceivedMessage message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(adminEmail);
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getContent());

        javaMailSender.send(mailMessage);
    }

    public void sendDefaultMessage(MessageData message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(adminEmail);
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getMessage());

        javaMailSender.send(mailMessage);
    }
}
