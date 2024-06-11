package edu.dadry.mailsender.server;

import edu.dadry.mailsender.messaging.EmailReceivedMessage;
import edu.dadry.mailsender.models.MessageData;
import edu.dadry.mailsender.repository.MessageRepository;
import edu.dadry.mailsender.service.MailSenderService;
import edu.dadry.mailsender.service.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MailSenderServiceIntegrationTest {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private MessageService messageService;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private MessageRepository messageRepository;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> mailMessageCaptor;

    @Captor
    private ArgumentCaptor<MessageData> messageDataCaptor;


    @Test
    void testSendBrokerMessage_success() {
        // Arrange
        EmailReceivedMessage emailReceivedMessage = EmailReceivedMessage.builder()
                .subject("Test Subject")
                .content("Test Content")
                .build();

        // Act
        messageService.processMessage(emailReceivedMessage);

        // Assert
        verify(javaMailSender, times(1)).send(mailMessageCaptor.capture());
        verify(messageRepository, times(2)).save(messageDataCaptor.capture());
    }

    @Test
    void testSendBrokerMessage_failure() {
        // Arrange
        EmailReceivedMessage emailReceivedMessage = EmailReceivedMessage.builder()
                .subject("Test Subject")
                .content("Test Content")
                .build();
        doThrow(new MailException("Mail sending failed") {}).when(javaMailSender).send(Mockito.any(SimpleMailMessage.class));

        // Act
        messageService.processMessage(emailReceivedMessage);

        // Assert
        verify(javaMailSender, times(1)).send(mailMessageCaptor.capture());
        verify(messageRepository, times(2)).save(messageDataCaptor.capture());
        MessageData messageData = new MessageData();
        messageData.setMessage(emailReceivedMessage.getContent());
        messageData.setSubject(emailReceivedMessage.getSubject());
        messageData.setAttempt(1);
        messageData.setSuccess(false);
        messageData.setErrorMessage("Mail sending failed: Mail sending failed");

        assertThat(messageData.getSuccess()).isFalse();
        assertThat(messageData.getErrorMessage()).isEqualTo("Mail sending failed: Mail sending failed");
    }
}