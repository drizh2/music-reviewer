package edu.dadry.mailsender.listeners;

import edu.dadry.mailsender.messaging.EmailReceivedMessage;
import edu.dadry.mailsender.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailReceivedListener {

    private final MessageService messageService;

    @KafkaListener(topics = "emailReceived", groupId = "music")
    public void receiveEmail(EmailReceivedMessage message) {
        System.out.println("Listener received email: " + message.getSubject());
        messageService.processMessage(message);
    }
}

