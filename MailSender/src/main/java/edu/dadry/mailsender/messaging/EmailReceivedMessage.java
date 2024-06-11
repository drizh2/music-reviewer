package edu.dadry.mailsender.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class EmailReceivedMessage {
    private String subject;
    private String content;
}
