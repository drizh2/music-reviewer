package edu.profitsoft.musicreviewer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.emailReceived}")
    private String emailReceivedTopic;

    @Bean
    public NewTopic emailReceivedTopic() {
        return new NewTopic(emailReceivedTopic, 2, (short) 1);
    }
}