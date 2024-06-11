package edu.dadry.mailsender.service;

import edu.dadry.mailsender.messaging.EmailReceivedMessage;
import edu.dadry.mailsender.models.MessageData;
import edu.dadry.mailsender.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MailSenderService mailSender;

    public MessageData createMessage(MessageData message) {
        return messageRepository.save(message);
    }

    public void processMessage(EmailReceivedMessage message) {
        MessageData messageData = new MessageData();
        messageData.setMessage(message.getContent());
        messageData.setSubject(message.getSubject());
        messageData.setAttempt(1);
        createMessage(messageData);

        try {
            mailSender.sendBrokerMessage(message);
            messageData.setSuccess(true);
            createMessage(messageData);
        } catch (Exception e) {
            messageData.setSuccess(false);
            messageData.setErrorMessage(e.getMessage());
            createMessage(messageData);
        }
    }

    public void sendMessage(MessageData message) {
        message.setAttempt(message.getAttempt() + 1);
        try {
            mailSender.sendDefaultMessage(message);
            message.setSuccess(true);
            createMessage(message);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setErrorMessage(e.getCause().getMessage() + ": " + e.getMessage());
            createMessage(message);
        }
    }
}
