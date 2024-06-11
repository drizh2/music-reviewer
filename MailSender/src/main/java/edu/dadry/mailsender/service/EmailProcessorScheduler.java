package edu.dadry.mailsender.service;

import edu.dadry.mailsender.models.MessageData;
import edu.dadry.mailsender.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmailProcessorScheduler {
    private final MessageRepository messageRepository;
    private final MessageService messageService;

    @Scheduled(fixedDelay = 5*60*1000)
    public void processErrorEmails() {
        System.out.println("Processing error emails");
        List<MessageData> messages = messageRepository.findBySuccess(false);

        for (MessageData message : messages) {
            messageService.sendMessage(message);
        }
    }
}
