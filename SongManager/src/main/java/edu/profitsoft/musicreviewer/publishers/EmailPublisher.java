package edu.profitsoft.musicreviewer.publishers;

import edu.profitsoft.musicreviewer.messaging.EmailReceivedMessage;
import edu.profitsoft.musicreviewer.model.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailPublisher {
    @Value("${kafka.topic.emailReceived}")
    private String emailReceivedTopic;

    private final KafkaTemplate<String, EmailReceivedMessage> kafkaTemplate;

    public void publish(Song song) {
        EmailReceivedMessage message = EmailReceivedMessage.builder()
                .subject(song.getTitle() + " song creation")
                .content(song.getArtist().getFullName() + " - " + song.getTitle() + " has been created!")
                .build();

        kafkaTemplate.send(emailReceivedTopic, message);
    }
}
